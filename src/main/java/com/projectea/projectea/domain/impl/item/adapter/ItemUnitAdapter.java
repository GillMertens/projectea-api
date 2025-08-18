package com.projectea.projectea.domain.impl.item.adapter;

import com.projectea.projectea.domain.impl.item.DTO.ItemUnitDto;
import com.projectea.projectea.domain.impl.item.entities.ItemUnit;
import com.projectea.projectea.domain.impl.item.services.ItemUnitService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ItemUnitAdapter {
    private final ItemUnitService itemUnitService;
    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        Converter<com.projectea.projectea.domain.impl.item.entities.Item, Long> itemToId = ctx -> ctx.getSource() == null ? null : ctx.getSource().getId();
        modelMapper.typeMap(ItemUnit.class, ItemUnitDto.class).addMappings(mapper ->
                mapper.using(itemToId).map(ItemUnit::getItem, ItemUnitDto::setItemId)
        );
    }

    public List<ItemUnitDto> getUnitsByItemIdDto(Long itemId) {
        return itemUnitService.getUnitsByItemId(itemId).stream()
                .map(unit -> modelMapper.map(unit, ItemUnitDto.class))
                .collect(Collectors.toList());
    }

    public ItemUnitDto getUnitByIdDto(UUID id) {
        ItemUnit unit = itemUnitService.getUnitById(id);
        return unit != null ? modelMapper.map(unit, ItemUnitDto.class) : null;
    }

    public ItemUnitDto createUnitDto(ItemUnit unit) {
        ItemUnit created = itemUnitService.createUnit(unit);
        return modelMapper.map(created, ItemUnitDto.class);
    }

    public ItemUnitDto updateUnitDto(UUID id, ItemUnit unit) {
        ItemUnit updated = itemUnitService.updateUnit(id, unit);
        return updated != null ? modelMapper.map(updated, ItemUnitDto.class) : null;
    }

    public void deleteUnit(UUID id) {
        itemUnitService.deleteUnit(id);
    }
}


