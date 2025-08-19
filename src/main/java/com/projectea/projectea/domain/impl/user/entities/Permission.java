package com.projectea.projectea.domain.impl.user.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    CREATE_RESERVATION("create:reservation"),
    READ_RESERVATION("read:reservation"),
    READ_ALL_RESERVATIONS("read:all_reservations"),
    UPDATE_RESERVATION("update:reservation"),
    DELETE_RESERVATION("delete:reservation"),
    CREATE_USER("create:user"),
    READ_USER("read:user"),
    UPDATE_USER("update:user"),
    DELETE_USER("delete:user"),
    CREATE_ADDRESS("create:address"),
    READ_ADDRESS("read:address"),
    UPDATE_ADDRESS("update:address"),
    DELETE_ADDRESS("delete:address"),
    CREATE_ITEM("create:item"),
    READ_ITEM("read:item"),
    UPDATE_ITEM("update:item"),
    DELETE_ITEM("delete:item"),
    CREATE_ITEM_UNIT("create:item_unit"),
    READ_ITEM_UNIT("read:item_unit"),
    UPDATE_ITEM_UNIT("update:item_unit"),
    DELETE_ITEM_UNIT("delete:item_unit"),
    READ_CATEGORY("read:category"),
    CREATE_CATEGORY("create:category"),
    DELETE_CATEGORY("delete:category");

    private final String permission;
}
