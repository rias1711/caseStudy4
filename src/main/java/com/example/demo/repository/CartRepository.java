package com.example.demo.repository;

import com.example.demo.model.Cart;
import com.example.demo.model.MyUser;
import com.example.demo.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Long> {
    Iterable<Cart> findAllByUser(MyUser user);

    Iterable<Cart> findAllByOrderNumberAndUser(Long orderNumber, MyUser user);

    Cart findByProductAndUserAndOrderNumber(Optional<Product> product, MyUser user, Long orderNumber);
}
