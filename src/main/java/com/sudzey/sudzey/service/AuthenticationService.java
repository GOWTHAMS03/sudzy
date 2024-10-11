package com.sudzey.sudzey.service;

import com.sudzey.sudzey.dto.JwtAuthenticationResponse;
import com.sudzey.sudzey.dto.LoginRequest;
import com.sudzey.sudzey.dto.RegisterRequest;
import com.sudzey.sudzey.model.User;

public interface AuthenticationService {
    User register(RegisterRequest registerRequest);
    public JwtAuthenticationResponse login(LoginRequest loginRequest);
    void logout(String token);
}