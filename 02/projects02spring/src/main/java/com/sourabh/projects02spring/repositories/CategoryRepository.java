package com.sourabh.projects02spring.repositories;

import com.sourabh.projects02spring.models.Category;
import com.sourabh.projects02spring.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category save(Category category);
    Category findByTitle(String title);
}
