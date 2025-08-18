package com.projectea.projectea.domain.impl.item.services;

import com.projectea.projectea.domain.impl.item.entities.ItemUnit;
import com.projectea.projectea.domain.impl.item.repositories.ItemUnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemUnitServiceImpl implements ItemUnitService {
    private final ItemUnitRepository itemUnitRepository;

    @Override
    public List<ItemUnit> getUnitsByItemId(Long itemId) {
        return itemUnitRepository.findByItem_Id(itemId);
    }

    @Override
    public ItemUnit getUnitById(UUID id) {
        return itemUnitRepository.findById(id).orElse(null);
    }

    @Override
    public ItemUnit createUnit(ItemUnit unit) {
        return itemUnitRepository.save(unit);
    }

    @Override
    public ItemUnit updateUnit(UUID id, ItemUnit unit) {
        ItemUnit existing = itemUnitRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setCondition(unit.getCondition());
        existing.setItem(unit.getItem());
        return itemUnitRepository.save(existing);
    }

    @Override
    public void deleteUnit(UUID id) {
        itemUnitRepository.deleteById(id);
    }
}


