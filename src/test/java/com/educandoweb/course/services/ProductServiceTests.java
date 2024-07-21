package com.educandoweb.course.services;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.educandoweb.course.entities.Product;
import com.educandoweb.course.repositories.ProductRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllShouldReturnListOfProducts() {
  
        Product product1 = new Product(1L, "Product 1", "Description 1", 100.0, "https://example.br");
        Product product2 = new Product(2L, "Product 2", "Description 2", 200.0, "https://example.br");
        
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        
        List<Product> result = productService.findAll();
        
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).containsExactlyInAnyOrder(product1, product2);
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() {
        
        Product mockProduct = new Product(1L, "Product 1", "Description 1", 100.0, "https://example.br");
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        
        Product result = productService.findById(1L);
        
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Product 1");
        assertThat(result.getDescription()).isEqualTo("Description 1");
        assertThat(result.getPrice()).isEqualTo(100.0);
    }
}
