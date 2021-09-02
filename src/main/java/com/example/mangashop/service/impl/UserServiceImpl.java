package com.example.mangashop.service.impl;

import com.example.mangashop.model.User;
import com.example.mangashop.model.exceptions.UserNotFoundException;

import com.example.mangashop.repository.UserRepository;
import com.example.mangashop.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    @Override
    public User findById(String username) {
        return this.userRepository.findById(username)
                .orElseThrow(()-> new UserNotFoundException(username));
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findById(s)
                .orElseThrow(()->new UsernameNotFoundException(s));
    }
}
