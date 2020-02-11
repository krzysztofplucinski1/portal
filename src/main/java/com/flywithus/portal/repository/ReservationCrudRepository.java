package com.flywithus.portal.repository;

import com.flywithus.portal.model.ReservationStatus;
import com.flywithus.portal.repository.entity.ReservationEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

interface ReservationCrudRepository extends CrudRepository<ReservationEntity, UUID> {
    @Modifying
    @Query("update ReservationEntity reservation set reservation.status = :status where reservation.id = :id")
    void updateStatus(@Param("id") UUID id, @Param("status") ReservationStatus status);
}
