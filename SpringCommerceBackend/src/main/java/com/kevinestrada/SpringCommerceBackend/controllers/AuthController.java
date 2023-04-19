package com.kevinestrada.SpringCommerceBackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kevinestrada.SpringCommerceBackend.payload.request.LoginRequest;

import jakarta.validation.Valid;

public class AuthController {
    // @Autowired
    // AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return null;
    }
}
