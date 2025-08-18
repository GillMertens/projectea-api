package com.projectea.projectea.domain.impl.reservation.controllers;

import com.projectea.projectea.domain.impl.reservation.DTO.ReservationResponseDto;
import com.projectea.projectea.domain.impl.reservation.adapter.ReservationAdapter;
import com.projectea.projectea.domain.impl.reservation.DTO.ReservationCreateDto;
import com.projectea.projectea.domain.impl.user.entities.User;
import com.projectea.projectea.domain.impl.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    private final ReservationAdapter reservationAdapter;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations() {
        List<ReservationResponseDto> reservations = reservationAdapter.getAllReservationsDto();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable Long id) {
        ReservationResponseDto reservation = reservationAdapter.getReservationByIdDto(id);
        if (reservation != null) {
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationCreateDto reservation) {
        ReservationResponseDto createdReservation = reservationAdapter.createReservationFromDto(reservation);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> updateReservation(@PathVariable Long id, @RequestBody ReservationCreateDto reservation) {
        ReservationResponseDto updatedReservation = reservationAdapter.updateReservationFromDto(id, reservation);
        if (updatedReservation != null) {
            return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationAdapter.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationResponseDto>> getReservationsByUserId(@PathVariable Long userId) {
        List<ReservationResponseDto> reservations = reservationAdapter.getReservationsByUserIdDto(userId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/user/me")
    public ResponseEntity<List<ReservationResponseDto>> getMyReservations(Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ReservationResponseDto> reservations = reservationAdapter.getReservationsByUserIdDto(user.getId());
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}