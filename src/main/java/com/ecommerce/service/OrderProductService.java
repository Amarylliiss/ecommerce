package com.ecommerce.service;

import com.ecommerce.model.OrderProduct;
import com.ecommerce.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProductService {
    @Autowired
    private OrderProductRepository orderProductRepository;

    public void addOrderProduct(OrderProduct orderProduct){
        orderProductRepository.save(orderProduct);
    }

}
