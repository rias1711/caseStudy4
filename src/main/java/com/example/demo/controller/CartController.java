package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.MyUser;
import com.example.demo.model.OrderDetails;
import com.example.demo.model.Product;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mustache.MustacheTemplateAvailabilityProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/cart")
public class CartController extends MainController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    private MyUser getUser() {
        String username = this.getPrincipal();
        if (username.contains("anonymousUser")) {
            return null;
        } else {
            return userService.findByUserName(username);
        }
    }

    @GetMapping()
    public ModelAndView showCart() {
        ModelAndView modelAndView = new ModelAndView("/cart/cart");
        Iterable<Cart> carts = cartService.findAllByOrderNumberAndUser(this.getUser().getOrderNumber(), this.getUser());
        modelAndView.addObject("carts", carts);
        double total = 0L;
        for (Cart cart : carts) {
            Optional<Product> product = productService.findById(cart.getProduct().getProductId());
            if (product.get().getProductQuantity() < cart.getQuantity()) {
                cart.setQuantity(product.get().getProductQuantity());
                cartService.save(cart);
            }
            total += cart.getQuantity() * cart.getProduct().getProductPrice();
        }
        modelAndView.addObject("total", total);
        return modelAndView;
    }

    @GetMapping("/addCart/{productId}")
    public ModelAndView addCart(@PathVariable Long productId) {
        Cart cart = cartService.findByProductAndUserAndOrderNumber(productService.findById(productId), this.getUser(), this.getUser().getOrderNumber());
        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + 1);
            cartService.save(cart);
            return this.showCart();
        }
        cart = new Cart();
        if (this.getUser().getOrderNumber() == null) {
            this.getUser().setOrderNumber(1L);
        }
        cart.setOrderNumber(this.getUser().getOrderNumber());
        cart.setQuantity(1L);
//        cart.setProduct(productService.findById(productId));
        cart.setUser(this.getUser());
        cartService.save(cart);
        return this.showCart();
    }

    @GetMapping("/list")
    public ModelAndView listCart() {
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("carts", cartService.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createCart() {
        return new ModelAndView("create");
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("cart")Cart cart) {
        cartService.save(cart);
        return "redirect:/cart/list";
    }

    @GetMapping("/buy")
    public String buy() {
        Iterable<Cart> carts = cartService.findAllByOrderNumberAndUser(this.getUser().getOrderNumber(), this.getUser());
        double sum = 0L;
        for (Cart cart : carts) {
            Optional<Product> product = productService.findById(cart.getProduct().getProductId());
            product.get().setProductQuantity(product.get().getProductQuantity() - cart.getQuantity());
            productService.save(product.get());
            sum += cart.getQuantity() * cart.getProduct().getProductPrice();
        }
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderNumber(this.getUser().getOrderNumber());
        orderDetails.setTotalPrice(sum);
        orderDetailsService.save(orderDetails);
        this.getUser().setOrderNumber(this.getUser().getOrderNumber() + 1);
        userService.save(this.getUser());
        return "redirect:/product";
    }
}
