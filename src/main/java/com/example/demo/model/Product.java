package com.example.demo.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;


@Entity
@Data
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Product_Id")
    private Long productId;

    @Column(name = "Product_Name")
    @NotNull
    private String productName;

    @Column(name = "Product_Price")
    @NotNull
    private double productPrice;

    @NotNull
    private Long productQuantity;

    private String img;
    @Transient
    private MultipartFile imgFile;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<Cart> carts;

}
