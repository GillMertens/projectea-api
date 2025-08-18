package com.projectea.projectea.domain.impl.item.DTO;

import com.projectea.projectea.domain.impl.item.entities.Condition;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class ItemUnitDto {
    private UUID id;
    private Long itemId;
    private Condition condition;
}


