package com.example.demo.service.impl;

import com.example.demo.model.Cart;
import com.example.demo.model.MyUser;
import com.example.demo.repository.CartRepository;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Override
    public Iterable<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Optional<Cart> findById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void remove(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public Iterable<Cart> findAllByOrderNumberAndUser(Long orderNumber, MyUser user) {
        return cartRepository.findAllByOrderNumberAndUser(orderNumber, user);
    }
}
