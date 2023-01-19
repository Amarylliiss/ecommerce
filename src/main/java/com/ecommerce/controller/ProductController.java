package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository Repo;

    @RequestMapping("/catalog")
    public ModelAndView getAllProducts() {
        ModelAndView mv = new ModelAndView("products");
        mv.addObject("products", Repo.findAll());
        return mv;
    }


    @GetMapping("/addProduct")
    public String showSignUpForm(Product product) {
        return "admin/add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(@Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/add-product";
        }

        Repo.save(product);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String showUserList(Model model) {
        model.addAttribute("products", Repo.findAll());
        return "admin/dashboard";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Product product = Repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("product", product);
        return "admin/update-product";
    }
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") int id, @Valid Product product,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            product.setId(id);
            return "update-user";
        }

        Repo.save(product);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Product product = Repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        Repo.delete(product);
        return "redirect:/admin/dashboard";
    }
}