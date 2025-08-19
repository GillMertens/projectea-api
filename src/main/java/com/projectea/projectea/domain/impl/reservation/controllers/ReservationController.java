package com.projectea.projectea.domain.impl.reservation.controllers;

import com.projectea.projectea.domain.impl.reservation.DTO.ReservationResponseDto;
import com.projectea.projectea.domain.impl.reservation.adapter.ReservationAdapter;
import com.projectea.projectea.domain.impl.reservation.DTO.ReservationCreateDto;
import com.projectea.projectea.domain.impl.user.entities.User;
import com.projectea.projectea.domain.impl.user.entities.Role;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    private final ReservationAdapter reservationAdapter;
    private final UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('read:all_reservations')")
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations() {
        List<ReservationResponseDto> reservations = reservationAdapter.getAllReservationsDto();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    /**
     * Retrieves a specific reservation by ID.
     * Users can only access their own reservations or admins can access any.
     * 
     * @param id The reservation ID
     * @return The reservation if found
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read:reservation')")
    public ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable Long id, Authentication authentication) {
        ReservationResponseDto reservation = reservationAdapter.getReservationByIdDto(id);
        if (reservation != null) {
            // Check if user is admin or owns the reservation
            String email = authentication != null ? authentication.getName() : null;
            if (email != null) {
                User user = userRepository.findByEmail(email).orElse(null);
                if (user != null && (user.getRole() == Role.ADMIN || reservation.getUser().getId().equals(user.getId()))) {
                    return new ResponseEntity<>(reservation, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create:reservation')")
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationCreateDto reservation) {
        ReservationResponseDto createdReservation = reservationAdapter.createReservationFromDto(reservation);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }

    /**
     * Updates a specific reservation by ID.
     * Users can only update their own reservations or admins can update any.
     * 
     * @param id The reservation ID
     * @param reservation The updated reservation data
     * @param authentication The current user's authentication
     * @return The updated reservation
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('update:reservation')")
    public ResponseEntity<ReservationResponseDto> updateReservation(@PathVariable Long id, @RequestBody ReservationCreateDto reservation, Authentication authentication) {
        // First check if the reservation exists and user has permission to update it
        ReservationResponseDto existingReservation = reservationAdapter.getReservationByIdDto(id);
        if (existingReservation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Check if user is admin or owns the reservation
        String email = authentication != null ? authentication.getName() : null;
        if (email != null) {
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null && (user.getRole() == Role.ADMIN || existingReservation.getUser().getId().equals(user.getId()))) {
                ReservationResponseDto updatedReservation = reservationAdapter.updateReservationFromDto(id, reservation);
                if (updatedReservation != null) {
                    return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    /**
     * Deletes a specific reservation by ID.
     * Users can only delete their own reservations or admins can delete any.
     * 
     * @param id The reservation ID
     * @param authentication The current user's authentication
     * @return No content on successful deletion
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('delete:reservation')")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id, Authentication authentication) {
        // First check if the reservation exists and user has permission to delete it
        ReservationResponseDto existingReservation = reservationAdapter.getReservationByIdDto(id);
        if (existingReservation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Check if user is admin or owns the reservation
        String email = authentication != null ? authentication.getName() : null;
        if (email != null) {
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null && (user.getRole() == Role.ADMIN || existingReservation.getUser().getId().equals(user.getId()))) {
                reservationAdapter.deleteReservation(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    /**
     * Retrieves reservations for a specific user by user ID.
     * Users can only access their own reservations or admins can access any user's reservations.
     * 
     * @param userId The user ID
     * @param authentication The current user's authentication
     * @return List of user's reservations
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('read:reservation')")
    public ResponseEntity<List<ReservationResponseDto>> getReservationsByUserId(@PathVariable Long userId, Authentication authentication) {
        // Check if user is admin or accessing their own reservations
        String email = authentication != null ? authentication.getName() : null;
        if (email != null) {
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null && (user.getRole() == Role.ADMIN || user.getId().equals(userId))) {
                List<ReservationResponseDto> reservations = reservationAdapter.getReservationsByUserIdDto(userId);
                return new ResponseEntity<>(reservations, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    /**
     * Retrieves reservations for the currently authenticated user.
     * 
     * @param authentication The current user's authentication
     * @return List of user's reservations
     */
    @GetMapping("/user/me")
    @PreAuthorize("hasAuthority('read:reservation')")
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