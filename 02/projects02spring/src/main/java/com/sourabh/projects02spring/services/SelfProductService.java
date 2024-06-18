package com.sourabh.projects02spring.services;

import com.sourabh.projects02spring.dtos.StoreDto;
import com.sourabh.projects02spring.exceptions.ProductNotFoundException;
import com.sourabh.projects02spring.models.Category;
import com.sourabh.projects02spring.models.Product;
import com.sourabh.projects02spring.repositories.CategoryRepository;
import com.sourabh.projects02spring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Product addProduct(String title, String description, String image, String category, double price) {
        Product newProduct = new Product();
        newProduct.setTitle(title);
        newProduct.setDescription(description);
        newProduct.setImage(image);
        newProduct.setPrice(price);

        Category categoryInDb = categoryRepository.findByTitle(category);

        if(categoryInDb == null){
            Category newcategory = new Category();
            newcategory.setTitle(category);
            categoryInDb = newcategory;
        }
        newProduct.setCategory(categoryInDb);
        Product savedProduct = productRepository.save(newProduct);
        return savedProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<String> getAllCategories() {
        List<Category> category = categoryRepository.findAll();
        List<String> categories = new ArrayList<>();
        for (Category c : category) {
            categories.add(c.getTitle());
        }
        return categories;
    }

    @Override
    public List<Product> getAllProductsByCategory(String category) {
        return productRepository.findAllByCategory_Title(category);
    }

    @Override
    public Product deleteProductById(Long productId) throws ProductNotFoundException {
        Product productToDelete = productRepository.findProductById(productId);
        if(productToDelete == null){
            throw new ProductNotFoundException("No product found for ID " + productId);
        }
        productRepository.delete(productToDelete);
        return productToDelete;
    }

    @Override
    public Product updateProduct(Long productId, String title, String Description, String image, String Category, double price) throws ProductNotFoundException {
        Product productToUpdate = productRepository.findProductById(productId);
        if(productToUpdate == null){
            throw new ProductNotFoundException("No product found for ID " + productId);
        }
        if(title != null){
            productToUpdate.setTitle(title);
        }
        if(Description != null){
            productToUpdate.setDescription(Description);
        }
        if(image != null){
            productToUpdate.setImage(image);
        }
        if(price != 0){
            productToUpdate.setPrice(price);
        }
        if(Category != null){
            Category categoryInDb = categoryRepository.findByTitle(Category);
            if(categoryInDb == null){
                Category newcategory = new Category();
                newcategory.setTitle(Category);
                categoryInDb = newcategory;
            }
            productToUpdate.setCategory(categoryInDb);
        }
        return productRepository.save(productToUpdate);
    }

}
