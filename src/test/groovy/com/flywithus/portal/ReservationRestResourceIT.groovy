package com.flywithus.portal


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

@AutoConfigureMockMvc
@SpringBootTest(classes = [PortalApplication.class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationRestResourceIT extends Specification {
    @Autowired TestRestTemplate restTemplate

    def "should cancel reservation"() {
        when:
            ResponseEntity response = restTemplate.exchange(UUID.randomUUID().toString(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class)

        then:
            response.statusCode == HttpStatus.NO_CONTENT
    }

    def "should return error when try cancel reservation older than 5 days"() {
        when:
            ResponseEntity response = restTemplate.exchange(UUID.randomUUID().toString(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class)

        then:
            response.statusCode == HttpStatus.BAD_REQUEST
    }

    def "should return error when reservation not exist"() {
        when:
            ResponseEntity response = restTemplate.exchange(UUID.randomUUID().toString(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class)

        then:
            response.statusCode == HttpStatus.BAD_REQUEST
    }

    def "should return error when try cancel unpaid reservation"() {
        when:
            ResponseEntity response = restTemplate.exchange(UUID.randomUUID().toString(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class)

        then:
            response.statusCode == HttpStatus.BAD_REQUEST
    }
}
