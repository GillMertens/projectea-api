package com.projectea.projectea.domain.impl.reservation.DTO;

import java.util.List;

public class ReservationCreateDto{
    private Long userId;
    private String status;
    private String pickupDate;
    private String returnDate;
    private List<Long> itemId;
}
