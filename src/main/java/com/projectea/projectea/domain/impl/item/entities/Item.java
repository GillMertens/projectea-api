package com.projectea.projectea.domain.impl.item.entities;
import com.projectea.projectea.domain.impl.category.entities.Category;
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
    private String name;
    private String description;
    private String brand;
    private Condition condition;
    private double price;
    private String category;
}
