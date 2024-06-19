package com.sourabh.projects02spring.repositories.projections;

import com.sourabh.projects02spring.models.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;

public interface ProductProjection {
    Long getId();
    String getTitle();
    String getDescription();
    Double getPrice();
    String getImage();
    Category getCategory();
}
