package com.example.auth.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import com.example.auth.security.JwtUtil;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String registerUser(String username, String email, String password) {
        if (username == null || email == null || password == null || password.isBlank()) {
            return "Username, email, and password must not be empty!";
        }

        if (userRepository.findByUsername(username).isPresent()) {
            return "Username already exists!";
        }

        if (userRepository.findByEmail(email).isPresent()) {
            return "Email already registered!";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return "Registered Successfully!";
    }

    public String loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return jwtUtil.generateToken(username);
        }

        return "Invalid username or password";
    }
}
