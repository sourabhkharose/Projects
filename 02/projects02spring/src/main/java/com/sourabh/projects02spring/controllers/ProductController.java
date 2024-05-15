package com.sourabh.projects02spring.controllers;

import com.sourabh.projects02spring.dtos.ProductResponseDto;
import com.sourabh.projects02spring.models.Product;
import com.sourabh.projects02spring.services.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductDetails(@PathVariable("id") int productId){
        return productService.getSingleProduct(productId);
    }

    @PostMapping("/products")
    public ProductResponseDto addProduct(@RequestBody ProductResponseDto productResponseDto){
        return productService.addProduct(
                productResponseDto.getTitle(),
                productResponseDto.getDescription(),
                productResponseDto.getImage(),
                productResponseDto.getCategory(),
                productResponseDto.getPrice()
        );
    }
}
