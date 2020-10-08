package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Account_Id")
    private Long id;

    @Column(name = "User_Name")
    @NotNull
    private String userName;

    @Column(name = "Encryted_Password")
    @NotNull
    private String encryptedPassword;

    @Column(name = "User_Role")
    @NotNull
    @ManyToOne
    private Role role;
}
