package com.example.mangashop.service;

import com.example.mangashop.model.User;

public interface AuthService {
    User getCurrentUser();
    String getCurrentUserId();
}
