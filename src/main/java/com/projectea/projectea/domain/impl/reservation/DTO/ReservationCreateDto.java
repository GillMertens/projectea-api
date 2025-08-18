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
    private String returnDate;
    private List<UUID> unitIds;
}
