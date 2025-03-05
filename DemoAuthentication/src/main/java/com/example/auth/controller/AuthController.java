package com.example.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.auth.model.User;
import com.example.auth.service.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(User user, Model model) {
        String message = authService.registerUser(user.getUsername(), user.getEmail(), user.getPassword());
        model.addAttribute("message", message);
        return "register";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(User user, Model model) {
        String token = authService.loginUser(user.getUsername(), user.getPassword());

        if (token.startsWith("Invalid")) {
            model.addAttribute("message", "Invalid username or password");
            return "login";
        } else {
            model.addAttribute("message", "Login successful!");
            model.addAttribute("token", token);
            return "home";
        }
    }
}
