package com.projectea.projectea.domain.impl.reservation.repositories;

import com.projectea.projectea.domain.impl.reservation.entities.Reservation;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser_Id(Long userId);
    List<Reservation> findByUnits_IdInAndReturnDateAfterAndPickupDateBefore(List<UUID> unitIds, LocalDateTime after, LocalDateTime before);
}
