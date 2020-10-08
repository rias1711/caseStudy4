package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Data
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Product_Id")
    private Long id;

    @Column(name = "Product_Name")
    @NotNull
    private String name;

    @Column(name = "Product_Price")
    @NotNull
    private double price;

    @Lob
    @Column(name = "Product_Image")
    private byte[] image;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Create_Date")
    private Date createDate;
}
