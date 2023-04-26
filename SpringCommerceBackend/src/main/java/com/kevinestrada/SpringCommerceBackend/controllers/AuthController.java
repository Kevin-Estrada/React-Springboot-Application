package com.kevinestrada.SpringCommerceBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kevinestrada.SpringCommerceBackend.payload.request.LoginRequest;
import com.kevinestrada.SpringCommerceBackend.payload.request.SignupRequest;
import com.kevinestrada.SpringCommerceBackend.payload.response.JwtResponse;
import com.kevinestrada.SpringCommerceBackend.payload.response.MessageResponse;
import com.kevinestrada.SpringCommerceBackend.services.implementations.AuthServiceImpl;
import com.kevinestrada.SpringCommerceBackend.services.utility.AuthenticatedUserResponse;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthServiceImpl authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        AuthenticatedUserResponse authUserResponse = authService.authenticateRequest(loginRequest);
        System.out.println(authUserResponse.toString());
        return ResponseEntity.ok(new JwtResponse(authUserResponse.getJwt(), authUserResponse.getUserDetails().getId(),
                authUserResponse.getUserDetails().getUsername(), authUserResponse.getUserDetails().getEmail(),
                authUserResponse.getRoles()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (authService.doesUsernameExist(signupRequest)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        if (authService.doesEmailExist(signupRequest)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        return ResponseEntity.ok(authService.signupUser(signupRequest));
    }
}
