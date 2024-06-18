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
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;
    private ModelMapper modelMapper;

    public ProductController(@Qualifier("storeProductService") ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponseDto> getSingleProduct(@PathVariable("id") Long productId) throws ProductNotFoundException {
        Product product = productService.getSingleProduct(productId);
        return new ResponseEntity<>(convertToProductResponseDto(product), HttpStatus.OK);
    }

    @PostMapping()
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

    @GetMapping()
    public List<ProductResponseDto> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products){
            productResponseDtos.add(convertToProductResponseDto(product));
        }
        return productResponseDtos;
    }

    @GetMapping("categories")
    public ResponseEntity<List<String>> getAllCategories(){
        return new ResponseEntity<>(productService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<ProductResponseDto>> getAllProductsByCategory(@PathVariable("category")String category) throws ProductNotFoundException {
        List<Product> products = productService.getAllProductsByCategory(category);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products){
            productResponseDtos.add(convertToProductResponseDto(product));
        }
        return new ResponseEntity<>(productResponseDtos, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ProductResponseDto> deleteProductById(@PathVariable("id") Long productId) throws ProductNotFoundException {
        Product product = productService.deleteProductById(productId);
        ProductResponseDto productResponseDto = convertToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long productId, @RequestBody ProductResponseDto productResponseDto) throws ProductNotFoundException {
        Product product = productService.updateProduct(productId, productResponseDto.getTitle(), productResponseDto.getDescription(), productResponseDto.getImage(), productResponseDto.getCategory(), productResponseDto.getPrice());
        return new ResponseEntity<>(convertToProductResponseDto(product), HttpStatus.OK);
    }

    private ProductResponseDto convertToProductResponseDto(Product product){
        String categoryTitle = product.getCategory().getTitle();
        ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
        productResponseDto.setCategory(categoryTitle);
        return productResponseDto;
    }
}
