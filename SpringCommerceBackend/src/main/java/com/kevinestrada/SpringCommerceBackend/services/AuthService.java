package com.kevinestrada.SpringCommerceBackend.services;

import com.kevinestrada.SpringCommerceBackend.payload.request.LoginRequest;
import com.kevinestrada.SpringCommerceBackend.payload.request.SignupRequest;
import com.kevinestrada.SpringCommerceBackend.payload.response.MessageResponse;
import com.kevinestrada.SpringCommerceBackend.services.utility.AuthenticatedUserResponse;

public interface AuthService {
    AuthenticatedUserResponse authenticateRequest(LoginRequest loginRequest);

    MessageResponse signupUser(SignupRequest signupRequest);
}
