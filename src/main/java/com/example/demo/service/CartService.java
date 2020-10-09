package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.model.MyUser;

public interface CartService extends GenericService<Cart> {
    Iterable<Cart> findAllByOrderNumberAndUser(Long orderNumber, MyUser user);
}
