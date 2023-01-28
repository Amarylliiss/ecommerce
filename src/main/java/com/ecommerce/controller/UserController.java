package com.ecommerce.controller;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String homePage(){
        return "index";
    }

    @GetMapping("/user-list")
    public String viewUser(Model model){
        model.addAttribute("user",userRepository.findAll());
        return "admin/user-list";
    }
    @GetMapping("/add-user")
    public String showAddUserForm(User user) {
        return "admin/add-user";
    }

    @PostMapping("/add-user")
    public String addUser(@Valid User user, BindingResult result, HttpSession session, Model model) {

        if (result.hasErrors()) {
            session.setAttribute("action", "Produkt nie zostal dodany");
            return "admin/add-user";
        }

        userRepository.saveAndFlush(user);
        return "redirect:/user-list";
    }

    @GetMapping("/edit-user/{id}")
    public String showUpdateUserForm(@PathVariable("id") long id, Model model) {
        Optional<User> user = userRepository.findById(id);


        model.addAttribute("user", user);
        return "admin/update-user";
    }

    @PostMapping("/update-user/{id}")
    public String updateUser(@PathVariable("id") int id, @Valid User user,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "admin/update-user";
        }

        userRepository.save(user);
        return "redirect:/user-list";
    }

//    @GetMapping("/delete/{id}")
//    public String deleteUser(@PathVariable("id") long id, Model model) {
//        User user = userRepository.findById(id);
//
//        userRepository.delete(user);
//        return "redirect:/user-list";
//    }

}