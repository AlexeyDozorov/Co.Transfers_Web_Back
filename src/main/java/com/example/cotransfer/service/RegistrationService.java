package com.example.cotransfer.service;

import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {
    void register(String email);
}
