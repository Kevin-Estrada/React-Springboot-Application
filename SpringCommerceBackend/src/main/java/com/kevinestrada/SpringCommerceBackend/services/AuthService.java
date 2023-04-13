package com.kevinestrada.SpringCommerceBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kevinestrada.SpringCommerceBackend.repository.RoleRepository;
import com.kevinestrada.SpringCommerceBackend.repository.UserRepository;
import com.kevinestrada.SpringCommerceBackend.security.JwtUtils;

public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
}
