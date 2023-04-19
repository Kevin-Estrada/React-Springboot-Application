package com.kevinestrada.SpringCommerceBackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevinestrada.SpringCommerceBackend.repository.RoleRepository;
import com.kevinestrada.SpringCommerceBackend.repository.UserRepository;
import com.kevinestrada.SpringCommerceBackend.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

}
