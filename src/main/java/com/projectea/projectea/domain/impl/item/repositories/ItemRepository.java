package com.projectea.projectea.domain.impl.item.repositories;

import com.projectea.projectea.domain.impl.item.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
