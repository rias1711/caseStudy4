package com.example.demo.repository;

import com.example.demo.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    MyUser findByUserName(String username);
}
