package com.projectea.projectea.domain.impl.item.controllers;

import com.projectea.projectea.domain.impl.item.DTO.ItemUnitDto;
import com.projectea.projectea.domain.impl.item.adapter.ItemUnitAdapter;
import com.projectea.projectea.domain.impl.item.entities.ItemUnit;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/item-units")
@RequiredArgsConstructor
public class ItemUnitController {
    private final ItemUnitAdapter itemUnitAdapter;

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<ItemUnitDto>> getUnitsByItem(@PathVariable Long itemId) {
        List<ItemUnitDto> units = itemUnitAdapter.getUnitsByItemIdDto(itemId);
        return new ResponseEntity<>(units, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemUnitDto> getUnit(@PathVariable UUID id) {
        ItemUnitDto unit = itemUnitAdapter.getUnitByIdDto(id);
        return unit != null ? new ResponseEntity<>(unit, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ItemUnitDto> createUnit(@RequestBody ItemUnit unit) {
        ItemUnitDto created = itemUnitAdapter.createUnitDto(unit);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemUnitDto> updateUnit(@PathVariable UUID id, @RequestBody ItemUnit unit) {
        ItemUnitDto updated = itemUnitAdapter.updateUnitDto(id, unit);
        return updated != null ? new ResponseEntity<>(updated, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnit(@PathVariable UUID id) {
        itemUnitAdapter.deleteUnit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


