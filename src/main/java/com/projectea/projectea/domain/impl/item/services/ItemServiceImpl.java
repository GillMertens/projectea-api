package com.projectea.projectea.domain.impl.item.services;
import com.projectea.projectea.domain.impl.item.entities.Item;
import com.projectea.projectea.domain.impl.item.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    public List<Item> getItemsByCategory(String category) {
        return itemRepository.findByCategoryName(category);
    }

    @Override
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Long id, Item item) {
        Item existingItem = itemRepository.findById(id).orElse(null);
        if (existingItem == null) {
            return null;
        }
        existingItem.setName(item.getName());
        existingItem.setDescription(item.getDescription());
        existingItem.setBrand(item.getBrand());
        existingItem.setCategory(item.getCategory());
        return itemRepository.save(existingItem);
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
