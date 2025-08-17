package com.projectea.projectea.domain.impl.item.DTO;

import com.projectea.projectea.domain.impl.item.entities.Condition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private Condition condition;
    private double price;
    private Long categoryId;
}
