package com.kevinestrada.SpringCommerceBackend.services.implementations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kevinestrada.SpringCommerceBackend.models.ERole;
import com.kevinestrada.SpringCommerceBackend.models.Role;
import com.kevinestrada.SpringCommerceBackend.models.User;
import com.kevinestrada.SpringCommerceBackend.payload.request.LoginRequest;
import com.kevinestrada.SpringCommerceBackend.payload.request.SignupRequest;
import com.kevinestrada.SpringCommerceBackend.payload.response.MessageResponse;
import com.kevinestrada.SpringCommerceBackend.repository.RoleRepository;
import com.kevinestrada.SpringCommerceBackend.repository.UserRepository;
import com.kevinestrada.SpringCommerceBackend.security.jwt.JwtUtils;
import com.kevinestrada.SpringCommerceBackend.security.services.UserDetailsImpl;
import com.kevinestrada.SpringCommerceBackend.services.AuthService;
import com.kevinestrada.SpringCommerceBackend.services.utility.AuthenticatedUserResponse;

public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public AuthenticatedUserResponse authenticateRequest(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new AuthenticatedUserResponse(jwt, userDetails, roles);
    }

    public boolean doesUsernameExist(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean doesEmailExist(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public MessageResponse signupUser(SignupRequest signupRequest) {
        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Admin Role not found."));
                        roles.add(adminRole);
                        break;

                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Moderator Role not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: User Role not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return new MessageResponse("User registered successfully!");
    }

}
