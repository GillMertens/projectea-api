package com.projectea.projectea.domain.impl.reservation.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ReservationCreateDto{
    private Long userId;
    private String pickupDate; 
    private Integer durationDays; // server will calculate returnDate (max 7)
    private List<UUID> unitIds;
}
