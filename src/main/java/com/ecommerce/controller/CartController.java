package com.ecommerce.controller;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Product;
import com.ecommerce.service.CartService;
import com.ecommerce.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @PostMapping("/cart")
    public String addToCart(@RequestParam ("quantity") int quantity, HttpSession session){
        Cart cart = new Cart();
        Product product=(Product) session.getAttribute("product");
        cart.setProductId(product.getId());
        cart.setName(product.getName());
        cart.setQuantity(quantity);
        cart.setPrice(product.getPrice()*quantity);
        cartService.saveCart(cart);

        return "redirect:/products";
    }
    @GetMapping("/cart/{id}")
    public String cartProduct(@PathVariable("id")long id, HttpSession session, Model model){

        session.setAttribute("product",productService.getProductById(id));
        Cart cart = new Cart();
        model.addAttribute("cart",cart);
        return "cart";
    }

}
