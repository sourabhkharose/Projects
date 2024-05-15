package com.sourabh.projects02spring.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private int id;
    private String title;
    private String description;
    private Double price;
    private String image;
    private String category;
}
