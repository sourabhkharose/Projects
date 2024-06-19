package com.sourabh.projects02spring;

import com.sourabh.projects02spring.models.Product;
import com.sourabh.projects02spring.repositories.ProductRepository;
import com.sourabh.projects02spring.repositories.projections.ProductProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Projects02springApplicationTests {

    @Autowired
    private ProductRepository productRepository;

	@Test
	void testGetProductsWithCategoryName(){
		List<Product> productList = productRepository.getProductsWithCategoryName("new electronics");
		for (Product product : productList) {
			System.out.println(product.getTitle());
		}
	}

	@Test
	void testGetProductTitlesByCategoryName(){
		List<String> titleList = productRepository.getProductTitlesByCategoryName("jewelery");
		for(String title : titleList){
			System.out.println(title);
		}
	}

	@Test
	void testGetProductIdAndTitleByCategoryName(){
		List<ProductProjection> products = productRepository.getProductIdAndTitleByCategoryName("jewelery");
		for (ProductProjection p : products){
			System.out.println("ID: " + p.getId());
			System.out.println("Title: " + p.getTitle() + "\n");
		}
	}

	@Test
	void testNativeQuery(){
		List<Product> productsList = productRepository.getProductById(2L);
		for (Product p : productsList){
			System.out.println(p.getTitle());
		}
	}

}
