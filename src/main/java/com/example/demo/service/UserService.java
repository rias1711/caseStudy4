package com.example.demo.service;

import com.example.demo.exception.UserExisted;
import com.example.demo.model.MyUser;

public interface UserService extends GenericService<MyUser> {
    MyUser findByUserName(String username);
    boolean userExists(String username);
    void createNewUser(MyUser user) throws UserExisted;

    boolean checkLogin(MyUser user);
}
