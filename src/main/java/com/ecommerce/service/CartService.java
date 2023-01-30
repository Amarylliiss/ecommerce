package com.ecommerce.service;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    public void saveCart(Cart cart){
        cartRepository.save(cart);
    }

    public void cartDeleteAll(){ cartRepository.deleteAll();}

    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }
}
