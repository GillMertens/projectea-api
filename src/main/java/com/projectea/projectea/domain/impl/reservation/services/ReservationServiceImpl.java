package com.projectea.projectea.domain.impl.reservation.services;

import com.projectea.projectea.domain.impl.reservation.entities.Reservation;
import com.projectea.projectea.domain.impl.reservation.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository reservationRepository;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        return reservation.orElse(null);
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(Long id, Reservation reservation) {
        return reservationRepository.findById(id).map(existingReservation -> {
            existingReservation.setUserId(reservation.getUserId());
            existingReservation.setStatus(reservation.getStatus());
            existingReservation.setPickupDate(reservation.getPickupDate());
            existingReservation.setReturnDate(reservation.getReturnDate());
            existingReservation.setItems(reservation.getItems());
            return reservationRepository.save(existingReservation);
        }).orElse(null);
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }
}