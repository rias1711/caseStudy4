package com.example.demo.service;

import com.example.demo.model.MyUser;

public interface UserService extends GenericService<MyUser> {
    MyUser findByName(String username);
    boolean userExists(String username);
    void createNewUser(MyUser user);
}
