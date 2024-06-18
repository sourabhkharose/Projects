package com.sourabh.projects02spring.services;

import com.sourabh.projects02spring.exceptions.ProductNotFoundException;
import com.sourabh.projects02spring.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    Product getSingleProduct(Long productId) throws ProductNotFoundException;

    Product addProduct(
            String title,
            String Description,
            String image,
            String Category,
            double price
    );

    List<Product> getAllProducts();

    List<String> getAllCategories();

    List<Product> getAllProductsByCategory(String category) throws ProductNotFoundException;

    Product deleteProductById(Long productId) throws ProductNotFoundException;

    Product updateProduct(Long productId, String title, String Description, String image, String Category, double price) throws ProductNotFoundException;
}

