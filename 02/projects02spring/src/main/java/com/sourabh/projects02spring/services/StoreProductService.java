package com.sourabh.projects02spring.services;

import com.sourabh.projects02spring.dtos.StoreDto;
import com.sourabh.projects02spring.exceptions.ProductNotFoundException;
import com.sourabh.projects02spring.models.Product;
import org.aspectj.weaver.bcel.FakeAnnotation;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("storeProductService")
public class StoreProductService implements ProductService {

    private RestTemplate restTemplate = new RestTemplate();

    public StoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        StoreDto response = restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, StoreDto.class);

        if (response == null) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found.");
        }

        return response.toProduct();
    }

    public Product addProduct(String title, String description, String image, String category, double price) {
        StoreDto storeDto = new StoreDto();

        storeDto.setTitle(title);
        storeDto.setDescription(description);
        storeDto.setImage(image);
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

    @Override
    public List<String> getAllCategories() {
        return restTemplate.getForObject("https://fakestoreapi.com/products/categories", List.class);
    }

    @Override
    public List<Product> getAllProductsByCategory(String category) {
        return restTemplate.getForObject("https://fakestoreapi.com/products/category/"+category, List.class);
    }

    @Override
    public Product deleteProductById(Long productId) throws ProductNotFoundException {
        StoreDto productToDelete = restTemplate.exchange("https://fakestoreapi.com/products/" + productId, HttpMethod.DELETE,null, StoreDto.class).getBody();
        if (productToDelete == null) {
            throw new ProductNotFoundException("No product found for ID " + productId);
        }
        return productToDelete.toProduct();
    }
}
