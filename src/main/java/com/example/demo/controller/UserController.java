package com.example.demo.controller;

import com.example.demo.model.MyUser;
import com.example.demo.service.ProductService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/home")
public class UserController extends MainController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleService roleService;

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/home/login";
    }

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("username", getPrincipal());
        return modelAndView;
    }

    @GetMapping("/signUp")
    public ModelAndView formSignUp() {
        ModelAndView modelAndView = new ModelAndView("signUp");
        modelAndView.addObject("user", new MyUser());
        return modelAndView;
    }

    @PostMapping("/signUp")
    public ModelAndView saveUser(@ModelAttribute("user")@Valid MyUser user) {
        userService.createNewUser(user);
        ModelAndView modelAndView = new ModelAndView("signUp");
        modelAndView.addObject("message", "Registed Successfully");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/editUser")
    public ModelAndView formEdit() {
        ModelAndView modelAndView = new ModelAndView("editUser");
        modelAndView.addObject("user", userService.findByName(getPrincipal()));
        return modelAndView;
    }

    @PostMapping("/editUser")
    public ModelAndView saveEdit(@ModelAttribute("user")@Valid MyUser user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole(roleService.findByName("ROLE_USER"));
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("editUser");
        modelAndView.addObject("message", "Update successfully");
        return modelAndView;
    }

    @GetMapping("/listUser")
    public ModelAndView listUser() {
        Iterable<MyUser> users = userService.findAll();
        ModelAndView modelAndView = new ModelAndView("listUser");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/listUser/delete/{userId}")
    public ModelAndView showDeleteForm(@PathVariable Long userId) {
        Optional<MyUser> user = userService.findById(userId);
        ModelAndView modelAndView;
        if (user != null) {
            modelAndView = new ModelAndView("deleteUser");
            modelAndView.addObject("user", user);
        } else {
            modelAndView = new ModelAndView("error");
        }
        return modelAndView;
    }

    @PostMapping("/listUser/delete")
    public String deleteUser(@ModelAttribute("user")MyUser user) {
        userService.remove(user.getUserId());
        return "redirect:/home/listUser";
    }
}
