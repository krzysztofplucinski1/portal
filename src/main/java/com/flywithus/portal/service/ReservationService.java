package com.flywithus.portal.service;

import com.flywithus.portal.model.Reservation;

import java.util.UUID;

public interface ReservationService {

    Reservation get(UUID id);

    void cancel(UUID id);

}
