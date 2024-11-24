package com.projectea.projectea.domain.impl.user.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet()),

    ADMIN(
        Set.of(
            Permission.CREATE_RESERVATION,
            Permission.READ_RESERVATION,
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
            Permission.CREATE_ITEM
        )
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .toList();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
