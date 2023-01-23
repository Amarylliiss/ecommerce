package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @RequestMapping("/product-list")
    public ModelAndView getAllProductsAdmin() {
        ModelAndView mv = new ModelAndView("admin/product-list");
        mv.addObject("productsAdm", Repo.findAll());
        return mv;
    }


    @GetMapping("/add-product")
    public String showAddProductForm(Product product) {
        return "admin/add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(@RequestParam MultipartFile file, @Valid Product product, BindingResult result, HttpSession session, Model model) {
        model.addAttribute("file", file);
        if (result.hasErrors()) {
//            session.setAttribute("action", "Produkt nie zostal dodany");
            return "admin/add-product";
        }

        Repo.saveAndFlush(product);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String showProductList(Model model) {
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
    public String updateProduct(@PathVariable("id") long id, @Valid Product product,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            product.setId(id);
            return "update-user";
        }

        Repo.save(product);
        return "redirect:/admin/dashboard";
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id, Model model) {
        Product product = Repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        Repo.delete(product);
        return "redirect:/admin/dashboard";
    }



//    @GetMapping("/page/{pageNo}")
//    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
//                                @RequestParam("sortField") String sortField,
//                                @RequestParam("sortDir") String sortDir,
//                                Model model) {
//        int pageSize = 5;
//
//        Page<Product> page =productService.findPaginated(pageNo, pageSize, sortField, sortDir);
//        List<Product> productList = page.getContent();
//
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//
//        model.addAttribute("sortField", sortField);
//        model.addAttribute("sortDir", sortDir);
//        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
//
//        model.addAttribute("productsAdm", productList);
//        return "index";
//    }
}
