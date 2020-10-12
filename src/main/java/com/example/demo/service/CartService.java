package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.model.MyUser;
import com.example.demo.model.Product;

import java.util.Optional;

public interface CartService extends GenericService<Cart> {
    Iterable<Cart> findAllByOrderNumberAndUser(Long orderNumber, MyUser user);

    Cart findByProductAndUserAndOrderNumber(Optional<Product> product, MyUser user, Long orderNumber);

    Long countByOrderNumberAndUser(Long orderNumber, MyUser user);
}
