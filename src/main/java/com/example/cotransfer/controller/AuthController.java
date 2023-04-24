package com.example.cotransfer.controller;

import com.example.cotransfer.dto.request.AuthRequest;
import com.example.cotransfer.dto.response.TokenResponse;
import com.example.cotransfer.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<TokenResponse> login(@RequestBody AuthRequest authRequest) {
        authService.authenticate(authRequest);
        return ResponseEntity.ok(authService.getToken(authRequest));
    }
}
