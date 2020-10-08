package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name = "Orders", uniqueConstraints = {@UniqueConstraint(columnNames = "Order_Num")})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Order_Id")
    private Long id;

    @Column(name = "Order_Date")
    @NotNull
    private Date orderDate;

    @Column(name = "Order_Num")
    @NotNull
    private int orderNum;

    @Column(name = "Order_Amount")
    @NotNull
    private double amount;

}
