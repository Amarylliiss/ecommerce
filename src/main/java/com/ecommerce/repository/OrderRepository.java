package com.ecommerce.repository;


import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findById(long id);
}


