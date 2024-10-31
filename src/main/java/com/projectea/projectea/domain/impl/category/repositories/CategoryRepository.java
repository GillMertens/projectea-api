package com.projectea.projectea.domain.impl.category.repositories;

import com.projectea.projectea.domain.impl.category.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
