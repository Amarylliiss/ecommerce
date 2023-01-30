package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import com.ecommerce.repository.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @GetMapping("/order-list")
    public String productsPage(HttpServletRequest request, Model model) {

    int page = 0;
    int size = 10;
    if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
        page = Integer.parseInt(request.getParameter("page")) - 1;
    }

    if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
        size = Integer.parseInt(request.getParameter("size"));
    }

        model.addAttribute("orders", orderRepository.findAll(PageRequest.of(page, size)));
    return "admin/order-list";

    }

}
