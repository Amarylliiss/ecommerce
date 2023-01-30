package com.ecommerce.controller;

import com.ecommerce.model.*;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderProductService orderProductService;
    @PostMapping("/cart")
    public String addToCart(@RequestParam ("quantity") int quantity, HttpSession session){
        Cart cart = new Cart();
        Product product=(Product) session.getAttribute("product");
        cart.setProductid(product.getId());
        cart.setName(product.getName());
        cart.setQuantity(quantity);
        cart.setPrice(product.getPrice()*quantity);
        cartService.saveCart(cart);

        return "redirect:/products";
    }
    @GetMapping("/cart/{id}")
    public String cart(@PathVariable("id")long id, HttpSession session, Model model){

        session.setAttribute("product",productService.getProductById(id));
        Cart cart = new Cart();
        model.addAttribute("cart",cart);
        return "cart";
    }
    @PostMapping("/cartproduct")
    public String cartProduct(@RequestParam("quantity") int quantity, HttpSession session ){

        Cart cart = new Cart();
        Product product =(Product) session.getAttribute("product");
        cart.setPrice(product.getPrice()*quantity);
        cart.setName(product.getName());
        cart.setQuantity(quantity);
        cart.setProductid(product.getId());
        cartService.saveCart(cart);
        return "redirect:/shopping-cart";
    }
    @GetMapping("/shopping-cart")
    public String showCart(Model model, HttpSession session){

        List<Cart> cartProduct= cartService.getAllCart();
        if(!cartProduct.isEmpty()) {
            model.addAttribute("cartProduct", cartProduct);
            model.addAttribute("action", session.getAttribute("action"));
            session.setAttribute("action", null);
            return "shopping-cart";
        } else {

            return "redirect:/catalog";
        }

    }
    @PostMapping("/order")
    public String order(HttpServletRequest request){

        List<Cart> cartProduct= cartService.getAllCart();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order = new Order();
        order.setUser(user);

        int totalCost=0;

        for (Cart c:cartProduct){
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProductid(c.getProductid());
            orderProduct.setAmount(c.getPrice());
            orderProduct.setQuantity(c.getQuantity());
            orderProduct.setOrder(order);
            totalCost+= c.getPrice();
        }
        order.setAmount(totalCost);
        orderService.saveOrder(order);

        for (Cart c:cartProduct){
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProductid(c.getProductid());
            orderProduct.setAmount(c.getPrice());
            orderProduct.setQuantity(c.getQuantity());
            orderProduct.setOrder(order);
            orderProductService.addOrderProduct(orderProduct);
        }


        cartService.cartDeleteAll();
        return "redirect:/";
    }
}
