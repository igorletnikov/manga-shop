package com.example.mangashop.service;

import com.example.mangashop.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

   User findById(String username);

}
