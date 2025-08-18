package com.projectea.projectea.domain.impl.item.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemUnitAvailabilityDto {
    private UUID unitId;
    private boolean available;
    private LocalDateTime reservedUntil; // null if available
}


