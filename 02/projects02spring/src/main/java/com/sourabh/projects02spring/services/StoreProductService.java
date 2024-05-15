package com.sourabh.projects02spring.services;

import com.sourabh.projects02spring.dtos.ProductResponseDto;
import com.sourabh.projects02spring.dtos.StoreDto;
import com.sourabh.projects02spring.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StoreProductService implements ProductService{

    private RestTemplate restTemplate = new RestTemplate();

    public StoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProductResponseDto getSingleProduct(int productId) {
        StoreDto storeDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId, StoreDto.class
        );
        return storeDto.toProductResponseDto();
    }

    public ProductResponseDto addProduct(
            String title,
            String description,
            String imageUrl,
            String category,
            double price){
        StoreDto storeDto = new StoreDto();

        storeDto.setTitle(title);
        storeDto.setDescription(description);
        storeDto.setImage(imageUrl);
        storeDto.setCategory(category);
        storeDto.setPrice(price);

        StoreDto response = restTemplate.postForObject(
                "https://fakestoreapi.com/products/", storeDto, StoreDto.class
        );

        return storeDto.toProductResponseDto();
    }
}
