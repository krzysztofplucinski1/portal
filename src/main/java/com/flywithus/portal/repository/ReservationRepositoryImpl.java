package com.flywithus.portal.repository;

import com.flywithus.portal.model.Reservation;
import com.flywithus.portal.model.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class ReservationRepositoryImpl implements ReservationRepository {
    private final ReservationCrudRepository reservationCrudRepository;

    @Override
    public Optional<Reservation> get(UUID id) {
        return Optional.empty();
    }

    @Override
    public void updateStatus(UUID id, ReservationStatus status) {
        reservationCrudRepository.updateStatus(id, status);
    }
}
