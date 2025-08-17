package com.projectea.projectea.domain.impl.reservation.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ReservationResponseDto {
    private Long id;
    private UserSummaryDto user;
    private String status;
    private LocalDateTime pickupDate;
    private LocalDateTime returnDate;
    private List<ItemSummaryDto> items;

}
