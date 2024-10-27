package com.projectea.projectea.domain.impl.category.entities;

import com.projectea.projectea.domain.impl.item.entities.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "category")
    private Set<Item> items;
}
