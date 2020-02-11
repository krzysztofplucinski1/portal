package com.flywithus.portal.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class Reservation {
    private UUID id;
    private LocalDateTime date;
    private ReservationStatus status;
    private BigDecimal amount;
}
