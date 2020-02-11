package com.flywithus.portal.service;

import com.flywithus.portal.exception.ReservationCannotBeCanceledException;
import com.flywithus.portal.exception.ReservationInvalidStatusException;
import com.flywithus.portal.exception.ReservationNotFoundException;
import com.flywithus.portal.model.Reservation;
import com.flywithus.portal.model.ReservationStatus;
import com.flywithus.portal.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    @Override
    public Reservation get(UUID id) {
        return reservationRepository.get(id)
                .orElseThrow(ReservationNotFoundException::new);
    }

    @Override
    @Transactional
    public void cancel(UUID id) {
        Reservation reservation = get(id);
        validate(reservation);
        reservationRepository.updateStatus(id, ReservationStatus.CANCELLED);
    }

    private void validate(Reservation reservation) {
        if (reservation.getStatus() != ReservationStatus.PAID) {
            throw new ReservationInvalidStatusException();
        }
        LocalDate reservationDate = reservation.getDate().toLocalDate();
        if (reservationDate.plusDays(5).isAfter(LocalDate.now())) {
            throw new ReservationCannotBeCanceledException();
        }
    }
}
