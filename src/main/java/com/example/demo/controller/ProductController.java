package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mustache.MustacheTemplateAvailabilityProvider;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping(value = "/product")
public class ProductController extends MainController {
    @Autowired
    Environment env;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public ModelAndView listProduct(@RequestParam("s")Optional<String> s, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/product/list");
        Page<Product> products;
        int pageNumber = 0;
        if (s.isPresent()) {
            products = productService.findAllByNameContaining(s.get(), pageable);
        } else {
            products = productService.findAll(pageable);
        }
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/edit-product/{id}")
    public ModelAndView showEditForm(@PathVariable Long productId) {
        Optional<Product> product = productService.findById(productId);
        ModelAndView modelAndView;
        if (product != null) {
            modelAndView = new ModelAndView("/product/edit");
            modelAndView.addObject("product", product);
        } else {
            modelAndView = new ModelAndView("/error");
        }
        return modelAndView;
    }

    @PostMapping("/edit-product")
    public ModelAndView updateProduct(@ModelAttribute("product")Product product) {
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product", product);
        modelAndView.addObject("message", "Product updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete-product/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        ModelAndView modelAndView;
        if (product != null) {
            modelAndView = new ModelAndView("/product/delete");
            modelAndView.addObject("product", product);
        } else {
            modelAndView = new ModelAndView("/error");
        }
        return modelAndView;
    }

    @PostMapping("/delete-product")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        productService.remove(product.getProductId());
        return "redirect:/product/list";
    }

    @GetMapping("/view-product/{id}")
    public ModelAndView viewProduct(@PathVariable("productId")Long productId) {
        Optional<Product> product = productService.findById(productId);
        ModelAndView modelAndView = new ModelAndView("product/view");
        modelAndView.addObject("product", product);
        return modelAndView;
    }

}
