package com.praveen.resumeanalyzer.service;

import com.praveen.resumeanalyzer.model.User;
import com.praveen.resumeanalyzer.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register

    public User register(User user) {

        user.setEmail(user.getEmail().trim().toLowerCase());

        if (userRepository.existsByEmail(user.getEmail())) {

            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(user);
    }

    // Login

    public User login(String email, String password) {

        email = email.trim().toLowerCase();

        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {

            return user;
        }

        return null;
    }

}