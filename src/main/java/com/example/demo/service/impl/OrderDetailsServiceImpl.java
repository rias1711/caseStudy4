package com.example.demo.service.impl;

import com.example.demo.model.OrderDetails;
import com.example.demo.repository.OrderDetailsRepository;
import com.example.demo.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Override
    public Iterable<OrderDetails> findAll() {
        return orderDetailsRepository.findAll();
    }

    @Override
    public Optional<OrderDetails> findById(Long orderId) {
        return orderDetailsRepository.findById(orderId);
    }

    @Override
    public void save(OrderDetails orderDetails) {
        orderDetailsRepository.save(orderDetails);
    }

    @Override
    public void remove(Long orderId) {
        orderDetailsRepository.deleteById(orderId);
    }
}
