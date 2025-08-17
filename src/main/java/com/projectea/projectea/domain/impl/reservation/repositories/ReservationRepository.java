package com.projectea.projectea.domain.impl.reservation.repositories;

import com.projectea.projectea.domain.impl.reservation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser_Id(Long userId);
}
