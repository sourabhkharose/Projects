package com.sourabh.projects02spring.services;

import com.sourabh.projects02spring.exceptions.ProductNotFoundException;
import com.sourabh.projects02spring.models.Category;
import com.sourabh.projects02spring.models.Product;
import com.sourabh.projects02spring.repositories.CategoryRepository;
import com.sourabh.projects02spring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("selfProductService")
public class SelfProductService implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findProductById(productId);
        if(product == null){
            throw new ProductNotFoundException("Product not found");
        }
        return product;
    }

    @Override
    public Product addProduct(String title, String description, String imageUrl, String Category, double price) {
        Product newProduct = new Product();
        newProduct.setTitle(title);
        newProduct.setDescription(description);
        newProduct.setImageUrl(imageUrl);
        newProduct.setPrice(price);

        Category category = categoryRepository.findByTitle(title);

        if(category == null){
            Category newcategory = new Category();
            newcategory.setTitle(title);
            category = newcategory;
        }
        newProduct.setCategory(category);
        Product savedProduct = productRepository.save(newProduct);
        return savedProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<String> getAllCategories() {
        return List.of(); //todo
    }

    @Override
    public List<Product> getAllProductsByCategory(String category) {
        return List.of(); //todo
    }
}
