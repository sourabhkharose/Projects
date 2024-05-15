package com.sourabh.projects02spring.services;

import com.sourabh.projects02spring.dtos.ProductResponseDto;
import com.sourabh.projects02spring.dtos.StoreDto;
import com.sourabh.projects02spring.models.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    public ProductResponseDto getSingleProduct(int productId);
    public ProductResponseDto addProduct(
            String title,
            String Description,
            String imageUrl,
            String Category,
            double price
    );
}

