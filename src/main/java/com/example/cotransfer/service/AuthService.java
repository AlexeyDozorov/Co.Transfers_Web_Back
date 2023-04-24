package com.example.cotransfer.service;


import com.example.cotransfer.dto.request.AuthRequest;
import com.example.cotransfer.dto.response.TokenResponse;
import org.jvnet.hk2.annotations.Service;

@Service
public interface AuthService {
    void authenticate(AuthRequest authRequest);
    TokenResponse getToken(AuthRequest authRequest);
}
