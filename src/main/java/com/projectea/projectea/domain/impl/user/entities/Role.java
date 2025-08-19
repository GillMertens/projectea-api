package com.projectea.projectea.domain.impl.user.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER(Set.of(
        Permission.READ_ITEM,
        Permission.READ_ITEM_UNIT,
        Permission.READ_CATEGORY,
        Permission.CREATE_RESERVATION,
        Permission.READ_RESERVATION,
        Permission.UPDATE_RESERVATION
    )),

    ADMIN(
        Set.of(
            Permission.CREATE_RESERVATION,
            Permission.READ_RESERVATION,
            Permission.READ_ALL_RESERVATIONS,
            Permission.UPDATE_RESERVATION,
            Permission.DELETE_RESERVATION,
            Permission.CREATE_USER,
            Permission.READ_USER,
            Permission.UPDATE_USER,
            Permission.DELETE_USER,
            Permission.CREATE_ADDRESS,
            Permission.READ_ADDRESS,
            Permission.UPDATE_ADDRESS,
            Permission.DELETE_ADDRESS,
            Permission.CREATE_ITEM,
            Permission.READ_ITEM,
            Permission.UPDATE_ITEM,
            Permission.DELETE_ITEM,
            Permission.CREATE_ITEM_UNIT,
            Permission.READ_ITEM_UNIT,
            Permission.UPDATE_ITEM_UNIT,
            Permission.DELETE_ITEM_UNIT,
            Permission.READ_CATEGORY,
            Permission.CREATE_CATEGORY,
            Permission.DELETE_CATEGORY
        )
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toCollection(ArrayList::new));

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
