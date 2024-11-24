package com.projectea.projectea.domain.impl.user.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    CREATE_RESERVATION("create:reservation"),
    READ_RESERVATION("read:reservation"),
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
    CREATE_ITEM("create:item");

    private final String permission;
}
