package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "Order_Details")
public class OrderDetail {
    @Id
    @Column(name = "Order_Details_Id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Order_Id", foreignKey = @ForeignKey(name = "Order_Detail_Ord_Fk"))
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Product_Id", foreignKey = @ForeignKey(name = "Order_Detail_Prod_Fk"))
    private Product product;

    @Column(name = "Quantity")
    @NotNull
    private int quantity;

    @Column(name = "Price")
    @NotNull
    private double price;

    @Column(name = "Amount")
    @NotNull
    private double amount;

}
