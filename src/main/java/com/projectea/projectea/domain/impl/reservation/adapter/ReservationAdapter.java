package com.projectea.projectea.domain.impl.reservation.adapter;

import com.projectea.projectea.domain.impl.reservation.DTO.ReservationResponseDto;
import com.projectea.projectea.domain.impl.item.DTO.ItemDto;
import com.projectea.projectea.domain.impl.reservation.entities.Reservation;
import com.projectea.projectea.domain.impl.reservation.services.ReservationService;
import com.projectea.projectea.domain.impl.reservation.DTO.ReservationCreateDto;
import com.projectea.projectea.domain.impl.item.repositories.ItemUnitRepository;
import com.projectea.projectea.domain.impl.item.entities.ItemUnit;
import com.projectea.projectea.domain.impl.item.entities.Item;
import com.projectea.projectea.domain.impl.user.repositories.UserRepository;
import com.projectea.projectea.domain.impl.user.entities.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ReservationAdapter {
    private final ReservationService reservationService;
    private final ModelMapper mm;
    private final ItemUnitRepository itemUnitRepository;
    private final UserRepository userRepository;

    public List<ReservationResponseDto> getAllReservationsDto() {
        return reservationService.getAllReservations().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ReservationResponseDto getReservationByIdDto(Long id) {
        Reservation reservation = reservationService.getReservationById(id);
        return reservation != null ? toDto(reservation) : null;
    }

    public ReservationResponseDto createReservationDto(Reservation reservation) {
        Reservation created = reservationService.createReservation(reservation);
        return toDto(created);
    }

    public ReservationResponseDto updateReservationDto(Long id, Reservation reservation) {
        Reservation updated = reservationService.updateReservation(id, reservation);
        return updated != null ? toDto(updated) : null;
    }

    public void deleteReservation(Long id) {
        reservationService.deleteReservation(id);
    }

    public List<ReservationResponseDto> getReservationsByUserIdDto(Long userId) {
        return reservationService.getReservationsByUserId(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ReservationResponseDto createReservationFromDto(ReservationCreateDto dto) {
        Reservation reservation = buildReservationFromDto(dto);
        Reservation created = reservationService.createReservation(reservation);
        return toDto(created);
    }

    public ReservationResponseDto updateReservationFromDto(Long id, ReservationCreateDto dto) {
        Reservation base = buildReservationFromDto(dto);
        Reservation updated = reservationService.updateReservation(id, base);
        return updated != null ? toDto(updated) : null;
    }

    private Reservation buildReservationFromDto(ReservationCreateDto dto) {
        Reservation reservation = new Reservation();
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        reservation.setUser(user);

        if (dto.getPickupDate() != null) {
            reservation.setPickupDate(LocalDateTime.parse(dto.getPickupDate()));
        }
        if (dto.getReturnDate() != null) {
            reservation.setReturnDate(LocalDateTime.parse(dto.getReturnDate()));
        }
        if (dto.getUnitIds() != null) {
            List<ItemUnit> units = dto.getUnitIds().stream()
                    .map(id -> itemUnitRepository.findById(id).orElse(null))
                    .filter(u -> u != null)
                    .collect(Collectors.toList());
            reservation.setUnits(units);
        }
        
        return reservation;
    }

    private ReservationResponseDto toDto(Reservation reservation) {
        ReservationResponseDto dto = mm.map(reservation, ReservationResponseDto.class);
        if (reservation.getUnits() == null || reservation.getUnits().isEmpty()) {
            dto.setItems(new ArrayList<>());
            return dto;
        }
        Map<Item, List<ItemUnit>> grouped = reservation.getUnits().stream()
                .collect(Collectors.groupingBy(ItemUnit::getItem));
        List<ItemDto> itemGroups = grouped.entrySet().stream()
                .map(entry -> {
                    Item item = entry.getKey();
                    List<ItemUnit> units = entry.getValue();
                    ItemDto dtoItem = mm.map(item, ItemDto.class);
                    dtoItem.setUnits(units.stream()
                            .map(u -> mm.map(u, com.projectea.projectea.domain.impl.item.DTO.ItemUnitSummaryDto.class))
                            .collect(Collectors.toList()));
                    return dtoItem;
                })
                .collect(Collectors.toList());
        dto.setItems(itemGroups);
        return dto;
    }
}
