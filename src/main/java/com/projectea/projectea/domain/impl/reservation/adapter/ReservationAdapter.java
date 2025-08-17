package com.projectea.projectea.domain.impl.reservation.adapter;

import com.projectea.projectea.domain.impl.reservation.DTO.ReservationResponseDto;
import com.projectea.projectea.domain.impl.reservation.entities.Reservation;
import com.projectea.projectea.domain.impl.reservation.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservationAdapter {
    private final ReservationService reservationService;
    private final ModelMapper mm;

    public List<ReservationResponseDto> getAllReservationsDto() {
        return reservationService.getAllReservations().stream()
                .map(reservation -> mm.map(reservation, ReservationResponseDto.class))
                .collect(Collectors.toList());
    }

    public ReservationResponseDto getReservationByIdDto(Long id) {
        Reservation reservation = reservationService.getReservationById(id);
        return reservation != null ? mm.map(reservation, ReservationResponseDto.class) : null;
    }

    public ReservationResponseDto createReservationDto(Reservation reservation) {
        Reservation created = reservationService.createReservation(reservation);
        return mm.map(created, ReservationResponseDto.class);
    }

    public ReservationResponseDto updateReservationDto(Long id, Reservation reservation) {
        Reservation updated = reservationService.updateReservation(id, reservation);
        return updated != null ? mm.map(updated, ReservationResponseDto.class) : null;
    }

    public void deleteReservation(Long id) {
        reservationService.deleteReservation(id);
    }

    public List<ReservationResponseDto> getReservationsByUserIdDto(Long userId) {
        return reservationService.getReservationsByUserId(userId).stream()
                .map(res -> mm.map(res, ReservationResponseDto.class))
                .collect(Collectors.toList());
    }
}
