package com.example.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String register(User user, RedirectAttributes redirectAttributes) {
        String message = authService.registerUser(user.getUsername(), user.getEmail(), user.getPassword());
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/auth/register"; // Redirect to show message
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(User user, RedirectAttributes redirectAttributes) {
        String token = authService.loginUser(user.getUsername(), user.getPassword());

        if (token.startsWith("Invalid")) {
            redirectAttributes.addFlashAttribute("message", "Invalid username or password");
            return "redirect:/auth/login"; // Stay on login page if invalid
        } else {
            // ✅ Pass the username in the URL
            return "redirect:/auth/home?login=success&user=" + user.getUsername();
        }
    }



    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
}
