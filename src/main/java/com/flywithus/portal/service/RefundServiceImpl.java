package com.flywithus.portal.service;

import com.flywithus.portal.external.PaymentRestClient;
import com.flywithus.portal.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
class RefundServiceImpl implements RefundService {
    private final PaymentRestClient paymentRestClient;

    public void refund(Reservation reservation) {
        paymentRestClient.refund(reservation.getId(), calculateRefundAmount(reservation.getAmount()));
    }

    private BigDecimal calculateRefundAmount(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(25))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
    }
}
