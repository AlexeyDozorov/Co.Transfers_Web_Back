package com.example.cotransfer.service.implementation;

import com.example.cotransfer.dto.request.AuthRequest;
import com.example.cotransfer.dto.response.TokenResponse;
import com.example.cotransfer.security.jwt.JwtProvider;
import com.example.cotransfer.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImplementation implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public void authenticate(AuthRequest authRequest) {
        log.info("Пользователь пытается пройти аутентификацию: " + authRequest.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        log.info("Пользователь прошел аутентификацию: " + authRequest.getUsername());
    }

    @Override
    public TokenResponse getToken(AuthRequest authRequest) {
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(jwtProvider.createToken(authRequest.getUsername()));
        log.info("Для пользователя сгенерировали JWT: " + authRequest.getUsername());
        return tokenResponse;
    }
}
