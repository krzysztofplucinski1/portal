package com.flywithus.portal.mapper;

import com.flywithus.portal.model.Reservation;
import com.flywithus.portal.repository.entity.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    @Mapping(source = "flight.date", target = "flightDate")
    Reservation map(ReservationEntity entity);
}
