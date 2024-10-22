package com.projectea.projectea.domain.impl.item.entities;
import com.projectea.projectea.domain.impl.category.entities.Condition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
    @Column(name = "category")
    private String category;
}
