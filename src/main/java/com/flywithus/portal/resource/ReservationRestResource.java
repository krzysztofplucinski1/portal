package com.flywithus.portal.resource;

import com.flywithus.portal.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("reservations")
public class ReservationRestResource {
    private final ReservationService reservationService;

    @DeleteMapping(path = "{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable("uuid") UUID id) {
        reservationService.cancel(id);
    }

}
