package com.flywithus.portal.resource

import com.flywithus.portal.PortalApplication
import com.flywithus.portal.external.PaymentRestClient
import com.flywithus.portal.model.ReservationStatus
import com.flywithus.portal.service.ReservationService
import groovy.sql.Sql
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Shared
import spock.lang.Specification

import javax.sql.DataSource
import java.time.LocalDate
import java.time.LocalDateTime

@AutoConfigureMockMvc
@SpringBootTest(classes = [PortalApplication.class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationRestResourceIT extends Specification {
    @Autowired TestRestTemplate restTemplate
    @Autowired ReservationService reservationService
    @Autowired DataSource dataSource
    @SpringBean PaymentRestClient paymentRestClient = Mock()
    @Shared UUID reservationId

    def setupSpec() {
        reservationId = UUID.randomUUID()
    }

    def cleanup() {
        Sql sql = new Sql(dataSource)
        sql.executeUpdate("delete reservations")
        sql.executeUpdate("delete flights")
    }

    def "should cancel reservation"() {
        given:
            def flightId = createFlight()
            createReservation(ReservationStatus.PAID, flightId)
            def url = UriComponentsBuilder.fromPath('/reservations/{uuid}')
                    .buildAndExpand(reservationId)
                    .toUriString()

        when:
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class)

        then:
            response.statusCode == HttpStatus.NO_CONTENT
            def reservation = reservationService.get(reservationId)
            reservation.status == ReservationStatus.CANCELLED
            1 * paymentRestClient.refund(reservationId, _ as BigDecimal)
    }

    def "should return error when try cancel reservation older than 5 days"() {
        given:
            def flightId = createFlight(LocalDate.now().plusDays(2))
            createReservation(ReservationStatus.PAID, flightId)
            def url = UriComponentsBuilder.fromPath('/reservations/{uuid}')
                    .buildAndExpand(reservationId)
                    .toUriString()

        when:
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class)

        then:
            response.statusCode == HttpStatus.BAD_REQUEST
            def reservation = reservationService.get(reservationId)
            reservation.status == ReservationStatus.PAID
            0 * paymentRestClient.refund(reservationId, _ as BigDecimal)
    }

    def "should return error when reservation not exist"() {
        given:
            def url = UriComponentsBuilder.fromPath('/reservations/{uuid}')
                    .buildAndExpand(reservationId)
                    .toUriString()
        when:
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class)

        then:
            response.statusCode == HttpStatus.BAD_REQUEST
            0 * paymentRestClient.refund(reservationId, _ as BigDecimal)
    }

    def "should return error when try cancel unpaid reservation"() {
        given:
            def flightId = createFlight()
            createReservation(ReservationStatus.NEW, flightId)
            def url = UriComponentsBuilder.fromPath('/reservations/{uuid}')
                    .buildAndExpand(reservationId)
                    .toUriString()

        when:
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class)

        then:
            response.statusCode == HttpStatus.BAD_REQUEST
            def reservation = reservationService.get(reservationId)
            reservation.status == ReservationStatus.NEW
            0 * paymentRestClient.refund(reservationId, _ as BigDecimal)
    }

    def createReservation(ReservationStatus status = ReservationStatus.NEW, UUID flightId = UUID.randomUUID()) {
        Sql sql = new Sql(dataSource)
        sql.executeUpdate("insert into reservations (id, date, status, amount, flight_id) values (?, ?, ?, ?, ?)",
                [reservationId.toString(), LocalDateTime.now(), status.name(), BigDecimal.TEN, flightId.toString()])
    }

    def createFlight(LocalDate date = LocalDate.now().plusDays(10)) {
        def flightId = UUID.randomUUID()
        Sql sql = new Sql(dataSource)
        sql.executeUpdate("insert into flights (id, date) values (?, ?)", [flightId, date])
        flightId
    }
}
