package com.example.demo.service.impl;

import com.example.demo.exception.UserExisted;
import com.example.demo.model.MyUser;
import com.example.demo.model.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleService roleService;

    @Override
    public Iterable<MyUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<MyUser> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void save(MyUser user) {
        userRepository.save(user);
    }

    @Override
    public void remove(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public MyUser findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByUserName(username) != null;
    }

    @Override
    public void createNewUser(MyUser user) throws UserExisted {
        if (userExists(user.getUserName())) {
            throw new UserExisted("Username existed!");
        }
        MyUser newUser = new MyUser();
        newUser.setUserName(user.getUserName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = new Role();
        role.setRoleId(2L);
        newUser.setRole(role);
        userRepository.save(newUser);
    }

    @Override
    public boolean checkLogin(MyUser user) {
        return (user.getUserName() != null);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = this.findByUserName(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(user.getRole());
        UserDetails userDetails = new User(user.getUserName(), user.getPassword(), authorities);
        return userDetails;
    }
}
