package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    @NotNull
    private String userName;

    @Column(name = "user_password")
    @NotNull
    private String password;

    @NotNull
    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<Cart> carts;

    private Long orderNumber;
}
