package com.sourabh.projects02spring.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private int id;
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    private Category category;
}
