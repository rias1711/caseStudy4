package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Customer_Id")
    private Long id;

    @Column(name = "Customer_Name")
    @NotNull
    private String customerName;

    @Column(name = "Customer_Address")
    @NotNull
    private String customerAddress;

    @Column(name = "Customer_Email")
    @NotNull
    @Email
    private String customerEmail;

    @Column(name = "Customer_Phone")
    @NotNull
    private String customerPhone;
}
