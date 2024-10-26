package com.projectea.projectea.domain.impl.item.services;

import com.projectea.projectea.domain.impl.item.entities.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAllItems();
    Item getItemById(Long id);
    Item createItem(Item item);
    Item updateItem(Long id, Item item);
    void deleteItem(Long id);
}
