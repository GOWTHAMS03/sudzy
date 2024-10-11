package com.sudzey.sudzey.controller;


import com.sudzey.sudzey.dto.JwtAuthenticationResponse;
import com.sudzey.sudzey.dto.LoginRequest;
import com.sudzey.sudzey.dto.RegisterRequest;
import com.sudzey.sudzey.model.User;
import com.sudzey.sudzey.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        authenticationService.logout(jwtToken);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}


























