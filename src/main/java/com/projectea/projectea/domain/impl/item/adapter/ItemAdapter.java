package com.projectea.projectea.domain.impl.item.adapter;

import com.projectea.projectea.domain.impl.item.DTO.ItemDto;
import com.projectea.projectea.domain.impl.item.DTO.ItemUnitSummaryDto;
import com.projectea.projectea.domain.impl.item.entities.Item;
import com.projectea.projectea.domain.impl.item.services.ItemService;
import com.projectea.projectea.domain.impl.category.entities.Category;
import com.projectea.projectea.domain.impl.item.entities.ItemUnit;
import com.projectea.projectea.domain.impl.item.entities.ItemImage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemAdapter {
    private final ItemService itemService;
    private final ModelMapper modelMapper;

    /**
     * Configures ModelMapper mappings for Item entity to ItemDto conversion.
     * Sets up custom converters for:
     * - Category entity to category ID
     * - Set of ItemUnits to List of ItemUnitSummaryDto
     * - List of ItemImages to List of image URLs
     */
    @PostConstruct
    public void configureMappings() {
        Converter<Category, Long> categoryToId = ctx -> ctx.getSource() == null ? null : ctx.getSource().getId();
        Converter<java.util.Set<ItemUnit>, java.util.List<ItemUnitSummaryDto>> unitsToSummaries = ctx -> ctx.getSource() == null ? java.util.Collections.emptyList() : ctx.getSource().stream()
                .map(unit -> modelMapper.map(unit, ItemUnitSummaryDto.class))
                .collect(java.util.stream.Collectors.toList());

        Converter<java.util.List<ItemImage>, java.util.List<String>> imagesToUrls = ctx -> ctx.getSource() == null ? java.util.Collections.emptyList() : ctx.getSource().stream()
                .map(ItemImage::getUrl)
                .collect(java.util.stream.Collectors.toList());

        modelMapper.typeMap(Item.class, ItemDto.class).addMappings(mapper -> {
                mapper.using(categoryToId).map(Item::getCategory, ItemDto::setCategoryId);
                mapper.using(unitsToSummaries).map(Item::getUnits, ItemDto::setUnits);
                mapper.using(imagesToUrls).map(Item::getImages, ItemDto::setImages);
            }
        );
    }

    public List<ItemDto> getAllItemsDto() {
        return itemService.getAllItems().stream()
                .map(item -> modelMapper.map(item, ItemDto.class))
                .collect(Collectors.toList());
    }

    public ItemDto getItemByIdDto(Long id) {
        Item item = itemService.getItemById(id);
        return item != null ? modelMapper.map(item, ItemDto.class) : null;
    }

    public List<ItemDto> getItemsByCategoryDto(String category) {
        return itemService.getItemsByCategory(category).stream()
                .map(item -> modelMapper.map(item, ItemDto.class))
                .collect(Collectors.toList());
    }

    public ItemDto createItemDto(Item item) {
        Item created = itemService.createItem(item);
        return modelMapper.map(created, ItemDto.class);
    }

    public ItemDto updateItemDto(Long id, Item item) {
        Item updated = itemService.updateItem(id, item);
        return updated != null ? modelMapper.map(updated, ItemDto.class) : null;
    }

    public void deleteItem(Long id) {
        itemService.deleteItem(id);
    }
}


