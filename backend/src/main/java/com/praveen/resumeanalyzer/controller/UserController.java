package com.praveen.resumeanalyzer.controller;

import com.praveen.resumeanalyzer.model.User;
import com.praveen.resumeanalyzer.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // ================= REGISTER =================

    @PostMapping("/register")
    public String register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) {

        try {

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);

            userService.register(user);

            return "redirect:/login.html";

        } catch (RuntimeException e) {

            return "redirect:/register.html?error=email";
        }
    }

    // ================= LOGIN =================

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session) {

        User user = userService.login(email, password);

        if (user != null) {

            session.setAttribute("userId", user.getId());

            System.out.println("\n========== LOGIN SUCCESS ==========");
            System.out.println("Session ID : " + session.getId());
            System.out.println("User ID    : " + session.getAttribute("userId"));
            System.out.println("Name       : " + user.getName());
            System.out.println("===================================\n");

            return "redirect:/dashboard.html";
        }

        System.out.println("LOGIN FAILED");

        return "redirect:/login.html";
    }

}