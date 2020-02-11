package com.flywithus.portal.handler;

import com.flywithus.portal.exception.ReservationCannotBeCanceledException;
import com.flywithus.portal.exception.ReservationInvalidStatusException;
import com.flywithus.portal.exception.ReservationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationRestResourceHandler {
    @ExceptionHandler({ReservationNotFoundException.class, ReservationCannotBeCanceledException.class, ReservationInvalidStatusException.class})
    public ResponseEntity handleReservationNotFoundException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
