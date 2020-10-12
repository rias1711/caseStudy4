package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class MainController {

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private UserService userService;


    @ModelAttribute("username")
    public String getPrincipal() {
        String username = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    @ModelAttribute("role")
    public String getRole() {
        String username = this.getPrincipal();
        if (username.contains("anonymousUser") ) {
            return null;
        } else {
            return userService.findByUserName(username).getRole().getRoleName();
        }
    }

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

}
