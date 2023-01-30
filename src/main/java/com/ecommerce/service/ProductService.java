package com.ecommerce.service;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> getAllProducts(String keyword) {
        return productRepository.findAll();
    }

    public List<Product>  getByKeyword(String keyword){
        return  productRepository.findByKeyword(keyword);
    }

    public Product getProductById(long id){
        return productRepository.findById(id);
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }


}

