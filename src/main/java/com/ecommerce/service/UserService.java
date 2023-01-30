package com.ecommerce.service;


import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface UserService {
    public void saveUser(User user);
    public List<Object> isUserPresent(User user);


}
