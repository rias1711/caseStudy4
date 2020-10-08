package com.example.demo.model.cart;

import com.example.demo.model.Customer;
import lombok.Data;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartInfo {
    private int orderNum;
    private Customer customer;

    private final List<CartLineInfo> cartLineInfos = new ArrayList<CartLineInfo>();

    public CartInfo() {}

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<CartLineInfo> getCartLineInfos() {
        return cartLineInfos;
    }

    private CartLineInfo findLineById(Long id) {
        for (CartLineInfo line : this.cartLineInfos) {
            if (line.getProduct().getId().equals(id)) {
                return line;
            }
        }
        return null;
    }


}
