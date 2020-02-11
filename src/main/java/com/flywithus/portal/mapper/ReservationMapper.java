package com.flywithus.portal.mapper;

import com.flywithus.portal.model.Reservation;
import com.flywithus.portal.repository.entity.ReservationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    Reservation map(ReservationEntity entity);
}
