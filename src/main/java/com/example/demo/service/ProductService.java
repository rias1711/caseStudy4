package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(Long productId);

    void save(Product product);

    void remove(Long productId);

    Page<Product> findAllByCategory(Category category, Pageable pageable);

    Page<Product> findAllByProductNameContaining(String productName, Pageable pageable);
}
