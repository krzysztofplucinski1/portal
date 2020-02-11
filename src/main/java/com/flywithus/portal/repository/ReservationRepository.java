package com.flywithus.portal.repository;

import com.flywithus.portal.model.Reservation;
import com.flywithus.portal.model.ReservationStatus;

import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {

    Optional<Reservation> get(UUID id);

    void updateStatus(UUID id, ReservationStatus status);

}
