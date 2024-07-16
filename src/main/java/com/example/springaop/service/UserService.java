package com.example.springaop.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String getUserById(Long id) {
        return "User with ID: " + id;
    }

}