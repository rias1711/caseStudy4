package com.example.demo.service;

import com.example.demo.model.Product;

import java.util.Optional;

public interface ProductService {
    Iterable<Product> getAllProducts();

    Optional<Product> findById(Long id);

    void saveProduct(Product product);

    void deleteProduct(Long id);
}
