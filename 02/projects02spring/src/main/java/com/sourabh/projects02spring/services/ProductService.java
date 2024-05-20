package com.sourabh.projects02spring.services;

import com.sourabh.projects02spring.dtos.ProductResponseDto;
import com.sourabh.projects02spring.dtos.StoreDto;
import com.sourabh.projects02spring.exceptions.ProductNotFoundException;
import com.sourabh.projects02spring.models.Category;
import com.sourabh.projects02spring.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    public Product getSingleProduct(Long productId) throws ProductNotFoundException;

    public Product addProduct(
            String title,
            String Description,
            String imageUrl,
            String Category,
            double price
    );

    public List<Product> getAllProducts();

    public List<String> getAllCategories();

    public List<Product> getAllProductsByCategory(String category);
}

