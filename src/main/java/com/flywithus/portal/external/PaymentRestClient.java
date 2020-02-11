package com.flywithus.portal.external;

import com.flywithus.portal.external.model.Payment;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentRestClient {
    Payment get(UUID reservationId);

    void refund(UUID reservationId, BigDecimal amount);
}
