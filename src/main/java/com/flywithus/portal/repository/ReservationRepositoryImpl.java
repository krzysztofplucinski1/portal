package com.flywithus.portal.repository;

import com.flywithus.portal.mapper.ReservationMapper;
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
    private final ReservationMapper reservationMapper;

    @Override
    public Optional<Reservation> get(UUID id) {
        return reservationCrudRepository.findById(id)
                .map(reservationMapper::map);
    }

    @Override
    public void updateStatus(UUID id, ReservationStatus status) {
        reservationCrudRepository.updateStatus(id, status);
    }
}
