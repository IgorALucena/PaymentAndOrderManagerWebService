package com.educandoweb.course.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.educandoweb.course.entities.Category;
import com.educandoweb.course.repositories.CategoryRepository;

@SpringBootTest
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;
    
    @Mock
    private CategoryRepository repository;
    
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Category cat1 = new Category(1L, "Electronics");
        Category cat2 = new Category(2L, "Books");
        List<Category> categories = Arrays.asList(cat1, cat2);
        when(repository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Electronics", result.get(0).getName());
        assertEquals("Books", result.get(1).getName());
    }

    @Test
    void testFindById() {
    	
        Category cat = new Category(1L, "Electronics");
        when(repository.findById(1L)).thenReturn(Optional.of(cat));

        Category result = categoryService.findById(1L);

        assertNotNull(result);
        assertEquals("Electronics", result.getName());
    }
}
