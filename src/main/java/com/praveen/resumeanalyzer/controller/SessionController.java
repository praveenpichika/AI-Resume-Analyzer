package com.praveen.resumeanalyzer.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @GetMapping("/check-session")
    public boolean checkSession(HttpSession session) {

        System.out.println("\n========== SESSION CHECK ==========");
        System.out.println("Session ID : " + session.getId());
        System.out.println("User ID    : " + session.getAttribute("userId"));
        System.out.println("===================================\n");

        return session.getAttribute("userId") != null;
    }
}