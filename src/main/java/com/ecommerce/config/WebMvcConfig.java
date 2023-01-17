package com.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("auth/login");
        registry.addViewController("/register").setViewName("auth/register");
        registry.addViewController("/admin/dashboard").setViewName("/admin/dashboard");
        registry.addViewController("/add-product").setViewName("/admin/add-product");
        registry.addViewController("/list-product").setViewName("/admin/list-product");
        registry.addViewController("/update-product").setViewName("/admin/update-product");
        registry.addViewController("/catalog").setViewName("/products");

    }
}
