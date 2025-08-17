package com.projectea.projectea.domain.impl.item.adapter;

import com.projectea.projectea.domain.impl.item.DTO.ItemDto;
import com.projectea.projectea.domain.impl.item.entities.Item;
import com.projectea.projectea.domain.impl.item.services.ItemService;
import com.projectea.projectea.domain.impl.category.entities.Category;
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

    @PostConstruct
    public void configureMappings() {
        Converter<Category, Long> categoryToId = ctx -> ctx.getSource() == null ? null : ctx.getSource().getId();
        modelMapper.typeMap(Item.class, ItemDto.class).addMappings(mapper ->
                mapper.using(categoryToId).map(Item::getCategory, ItemDto::setCategoryId)
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


