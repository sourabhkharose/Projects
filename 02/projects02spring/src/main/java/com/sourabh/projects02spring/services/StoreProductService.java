package com.sourabh.projects02spring.services;

import com.sourabh.projects02spring.dtos.ProductResponseDto;
import com.sourabh.projects02spring.dtos.StoreDto;
import com.sourabh.projects02spring.exceptions.ProductNotFoundException;
import com.sourabh.projects02spring.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreProductService implements ProductService {

    private RestTemplate restTemplate = new RestTemplate();

    public StoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(int productId) throws ProductNotFoundException {
        StoreDto response = restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, StoreDto.class);

        if (response == null) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found.");
        }

        return response.toProduct();
    }

    public Product addProduct(String title, String description, String imageUrl, String category, double price) {
        StoreDto storeDto = new StoreDto();

        storeDto.setTitle(title);
        storeDto.setDescription(description);
        storeDto.setImage(imageUrl);
        storeDto.setCategory(category);
        storeDto.setPrice(price);

        StoreDto response = restTemplate.postForObject("https://fakestoreapi.com/products/", storeDto, StoreDto.class);

        return response.toProduct();
    }

    public List<Product> getAllProducts() {
        StoreDto[] response = restTemplate.getForObject("https://fakestoreapi.com/products/", StoreDto[].class);

        List<Product> products = new ArrayList<>();
        for (StoreDto storeDto : response) {
            products.add(storeDto.toProduct());
        }
        return products;
    }
}
