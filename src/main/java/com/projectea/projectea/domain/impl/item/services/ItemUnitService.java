package com.projectea.projectea.domain.impl.item.services;

import com.projectea.projectea.domain.impl.item.entities.ItemUnit;

import java.util.List;
import java.util.UUID;

public interface ItemUnitService {
    List<ItemUnit> getUnitsByItemId(Long itemId);
    ItemUnit getUnitById(UUID id);
    ItemUnit createUnit(ItemUnit unit);
    ItemUnit updateUnit(UUID id, ItemUnit unit);
    void deleteUnit(UUID id);
}


