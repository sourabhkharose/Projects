package com.sourabh.projects02spring.dtos;

import com.sourabh.projects02spring.models.Category;
import com.sourabh.projects02spring.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String image;
    private String category;

    public Product toProduct() {
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImage(image);
        Category categoryObj = new Category();
        categoryObj.setTitle(category);
        product.setCategory(categoryObj);
        return product;
    }
}
