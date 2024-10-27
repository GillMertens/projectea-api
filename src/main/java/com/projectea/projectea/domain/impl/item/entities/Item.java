package com.projectea.projectea.domain.impl.item.entities;
import com.projectea.projectea.domain.impl.category.entities.Category;
import com.projectea.projectea.domain.impl.category.entities.Condition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "brand")
    private String brand;
    @Column(name = "item_condition")
    private Condition condition;
    @Column(name = "price")
    private double price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
