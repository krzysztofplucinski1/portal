package com.flywithus.portal.external;

import com.flywithus.portal.external.model.Payment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
class PaymentRestClientImpl implements PaymentRestClient {
    public Payment get(UUID reservationId) {
        // todo consume external API
        return Payment.builder()
                .paid(true)
                .build();
    }

    @Override
    public void refund(UUID reservationId, BigDecimal amount) {
        // todo consume external API
    }
}
