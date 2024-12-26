package com.projectea.projectea.domain.impl.item.repositories;

import com.projectea.projectea.domain.impl.item.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategoryName(String categoryName);
}
