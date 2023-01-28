package com.ecommerce.controller;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;
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
    public String usersPage(HttpServletRequest request, Model model, @Param("keyword") String keyword) {

        int page = 0;
        int size = 5;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        model.addAttribute("usersAdm", userRepository.findAll(PageRequest.of(page, size)));
        return "admin/user-list";

    }
    @GetMapping("/add-user")
    public String showAddUserForm(User user) {
        return "admin/add-user";
    }

    @PostMapping("/add-user")
    public String addUser(@Valid User user, BindingResult result, HttpSession session, Model model) {

        if (result.hasErrors()) {
            session.setAttribute("action", "User nie zostal dodany");
            return "admin/add-user";
        }

        userService.saveUser(user);
        return "redirect:/user-list";
    }

    @GetMapping("/edit-user/{id}")
    public String showUpdateUserForm(@PathVariable("id") long id, Model model) {
       Optional<User> temp = userRepository.findById(id);
        User user=temp.get();

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

        userService.saveUser(user);
        return "redirect:/user-list";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userRepository.deleteById(id);
        return "redirect:/user-list";
    }



}