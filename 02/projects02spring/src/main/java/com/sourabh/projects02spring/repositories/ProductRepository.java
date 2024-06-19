package com.sourabh.projects02spring.repositories;

import com.sourabh.projects02spring.models.Product;
import com.sourabh.projects02spring.repositories.projections.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);
    List<Product> findAll();
    Product findProductById(Long id);
    List<Product> findAllByCategory_Title(String title);

    @Query("select p from Product p where p.category.title = :categoryName")
    List<Product> getProductsWithCategoryName(String categoryName);

    @Query("select p.title as title from Product p where p.category.title = :categoryName")
    List<String> getProductTitlesByCategoryName(String categoryName);

    @Query("select p.id as id, p.title as title from Product p where p.category.title = :categoryName")
    List<ProductProjection> getProductIdAndTitleByCategoryName(String categoryName);

    @Query(value = "select * from Product p where p.id = :id", nativeQuery = true)
    List<Product> getProductById(Long id);
}
