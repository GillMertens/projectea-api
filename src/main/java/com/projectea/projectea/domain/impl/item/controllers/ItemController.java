package com.projectea.projectea.domain.impl.item.controllers;


import com.projectea.projectea.domain.impl.item.DTO.ItemDto;
import com.projectea.projectea.domain.impl.item.adapter.ItemAdapter;
import com.projectea.projectea.domain.impl.item.entities.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = ItemController.BASE_URL)
@RequiredArgsConstructor
public class ItemController {
    public static final String BASE_URL = "/api/v1/items";
    private final ItemAdapter itemAdapter;

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItems(@RequestParam(required = false) String category) {
        List<ItemDto> items = (category == null || category.isBlank())
                ? itemAdapter.getAllItemsDto()
                : itemAdapter.getItemsByCategoryDto(category);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable Long id) {
        ItemDto item = itemAdapter.getItemByIdDto(id);
        return item != null ? new ResponseEntity<>(item, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestBody Item item) {
        ItemDto created = itemAdapter.createItemDto(item);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable Long id, @RequestBody Item item) {
        ItemDto updated = itemAdapter.updateItemDto(id, item);
        return updated != null ? new ResponseEntity<>(updated, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemAdapter.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
