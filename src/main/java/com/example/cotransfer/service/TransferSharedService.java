package com.example.cotransfer.service;

import org.springframework.http.ResponseEntity;

public interface TransferSharedService {
    ResponseEntity<?> getSharedTransfers();

    ResponseEntity<?> setShared(Long id);

    ResponseEntity<?> createSharedTransferFromAirport(String transfer, Long id);
}
