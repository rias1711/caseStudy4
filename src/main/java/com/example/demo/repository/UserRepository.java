package com.example.demo.repository;

import com.example.demo.model.MyUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<MyUser, Long> {
    MyUser findByName(String username);
}
