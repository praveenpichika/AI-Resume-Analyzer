package com.praveen.resumeanalyzer.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "Logged Out Successfully";
    }
}