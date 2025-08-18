package com.projectea.projectea.domain.impl.item.repositories;

import com.projectea.projectea.domain.impl.item.entities.ItemUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemUnitRepository extends JpaRepository<ItemUnit, UUID> {
    List<ItemUnit> findByItem_Id(Long itemId);
}


