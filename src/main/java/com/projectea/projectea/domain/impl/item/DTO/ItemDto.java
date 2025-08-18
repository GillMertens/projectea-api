package com.projectea.projectea.domain.impl.item.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private String thumbnailUrl;
    private java.util.List<String> images;
    private Long categoryId;
    private java.util.List<ItemUnitSummaryDto> units;
}
