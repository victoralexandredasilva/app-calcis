package com.victoralexandre.appcalcis.repositories;

import com.victoralexandre.appcalcis.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findFirstByName(String name);
}
