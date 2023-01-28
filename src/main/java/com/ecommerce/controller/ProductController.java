package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ProductController {
    @Autowired
    private ProductRepository Repo;
    @Autowired
    private ProductService productService;


    @RequestMapping(path = {"/catalog","/search"})
        public String search(Model model, HttpSession session, String keyword) {
        if (keyword != null) {
            session.setAttribute("listProduct", productService.getByKeyword(keyword));
        } else {
            session.setAttribute("listProduct", productService.getAllProducts(keyword));

        }
        return "products";
    }


    @GetMapping("/add-product")
    public String showAddProductForm(Product product) {
        return "admin/add-product";
    }

    @PostMapping("/add-product")
    public String addProduct( @Valid Product product, BindingResult result, HttpSession session, Model model) {

        if (result.hasErrors()) {
            session.setAttribute("action", "Produkt nie zostal dodany");
            return "admin/add-product";
        }

        Repo.saveAndFlush(product);
        return "redirect:/product-list";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Product product = Repo.findById(id);


        model.addAttribute("product", product);
        return "admin/update-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") long id, @Valid Product product,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            product.setId(id);
            return "admin/update-product";
        }

        Repo.save(product);
        return "redirect:/product-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id, Model model) {
        Product product = Repo.findById(id);

        Repo.delete(product);
        return "redirect:/product-list";
    }

    @GetMapping("/product-list")
    public String productsPage(HttpServletRequest request, Model model, @Param("keyword") String keyword) {

        int page = 0;
        int size = 10;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

            model.addAttribute("productsAdm", Repo.findAll(PageRequest.of(page, size)));
        return "admin/product-list";

    }
}

