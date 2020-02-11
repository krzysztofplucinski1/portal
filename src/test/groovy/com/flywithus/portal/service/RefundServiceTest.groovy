package com.flywithus.portal.service

import com.flywithus.portal.configuration.ApplicationProperty
import com.flywithus.portal.external.PaymentRestClient
import com.flywithus.portal.model.Reservation
import spock.lang.Specification

class RefundServiceTest extends Specification {
    RefundService refundService
    PaymentRestClient paymentRestClient
    ApplicationProperty applicationProperty

    def setup() {
        paymentRestClient = Mock()
        applicationProperty = new ApplicationProperty()
        refundService = new RefundServiceImpl(paymentRestClient, applicationProperty)
    }

    def "should refund 25 percent of the reservation amount"() {
        given:
            def id = UUID.randomUUID()
            Reservation reservation = Reservation.builder()
                    .id(id)
                    .amount(BigDecimal.valueOf(100))
                    .build()
            applicationProperty.setRefundPercentage(25)
        when:
            refundService.refund(reservation)
        then:
            1 * paymentRestClient.refund(id, BigDecimal.valueOf(25))
    }
}
