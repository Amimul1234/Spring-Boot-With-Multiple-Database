package com.shopKpr.apps.medicine.repositories.jpa;

import com.shopKpr.apps.medicine.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
