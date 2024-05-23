package com.sourabh.projects02spring.controllers;

import com.sourabh.projects02spring.dtos.ErrorDto;
import com.sourabh.projects02spring.dtos.ProductResponseDto;
import com.sourabh.projects02spring.exceptions.ProductNotFoundException;
import com.sourabh.projects02spring.models.Category;
import com.sourabh.projects02spring.models.Product;
import com.sourabh.projects02spring.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;
    private ModelMapper modelMapper;

    public ProductController(@Qualifier("selfProductService") ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getSingleProduct(@PathVariable("id") Long productId) throws ProductNotFoundException {
        Product product = productService.getSingleProduct(productId);
        return new ResponseEntity<>(convertToProductResponseDto(product), HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductResponseDto productResponseDto){
        Product product =  productService.addProduct(
                productResponseDto.getTitle(),
                productResponseDto.getDescription(),
                productResponseDto.getImage(),
                productResponseDto.getCategory(),
                productResponseDto.getPrice()
        );
        return new ResponseEntity<>(convertToProductResponseDto(product), HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products){
            productResponseDtos.add(convertToProductResponseDto(product));
        }
        return productResponseDtos;
    }

    @GetMapping("/products/categories")
    public ResponseEntity<List<String>> getAllCategories(){
        return new ResponseEntity<>(productService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/products/category/{category}")
    public ResponseEntity<List<Product>> getAllProductsByCategory(@PathVariable("category")String category){
        return new ResponseEntity<>(productService.getAllProductsByCategory(category), HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> deleteProductById(@PathVariable("id") Long productId) throws ProductNotFoundException {
        Product product = productService.deleteProductById(productId);
        ProductResponseDto productResponseDto = convertToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    private ProductResponseDto convertToProductResponseDto(Product product){
        String categoryTitle = product.getCategory().getTitle();
        ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
        productResponseDto.setCategory(categoryTitle);
        return productResponseDto;
    }
}
