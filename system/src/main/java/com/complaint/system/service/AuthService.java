package com.complaint.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.complaint.system.dto.AuthResponse;
import com.complaint.system.dto.LoginRequest;
import com.complaint.system.dto.RegisterRequest;
import com.complaint.system.enums.Role;
import com.complaint.system.model.User;
import com.complaint.system.repository.UserRepository;
import com.complaint.system.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // REGISTER
    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return "Registration successful";
    }

    // LOGIN
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(
                token,
                user.getRole().name(),
                user.getName(),
                user.getId()
        );
    }
}
