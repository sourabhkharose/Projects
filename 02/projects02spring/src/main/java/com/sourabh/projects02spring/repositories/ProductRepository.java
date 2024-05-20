package com.sourabh.projects02spring.repositories;

import com.sourabh.projects02spring.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);
    List<Product> findAll();
    Product findProductById(Long id);
}
