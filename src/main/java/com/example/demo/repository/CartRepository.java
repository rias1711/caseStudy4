package com.example.demo.repository;

import com.example.demo.model.Cart;
import com.example.demo.model.MyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
    Iterable<Cart> findAllByOrderNumberAndUser(Long orderNumber, MyUser user);
}
