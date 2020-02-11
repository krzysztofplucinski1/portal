package com.flywithus.portal.service

import com.flywithus.portal.external.PaymentRestClient
import com.flywithus.portal.model.Reservation
import spock.lang.Specification

class RefundServiceTest extends Specification {
    RefundService refundService
    PaymentRestClient paymentRestClient

    def setup() {
        paymentRestClient = Mock()
        refundService = new RefundServiceImpl(paymentRestClient)
    }

    def "should refund 25 percent of the reservation amount"() {
        given:
            def id = UUID.randomUUID()
            Reservation reservation = Reservation.builder()
                    .id(id)
                    .amount(BigDecimal.valueOf(100))
                    .build()
        when:
            refundService.refund(reservation)
        then:
            1 * paymentRestClient.refund(id, BigDecimal.valueOf(25))
    }
}
