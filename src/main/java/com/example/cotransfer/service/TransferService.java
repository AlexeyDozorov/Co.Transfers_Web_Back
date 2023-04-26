package com.example.cotransfer.service;

import com.example.cotransfer.model.Transfer;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.jvnet.hk2.annotations.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
@Service
public interface TransferService {



    ResponseEntity<?> deleteTransfer(String transfer);


    ResponseEntity<?> createTransferFromAirport(String transfer);

    ResponseEntity<?> updateTransfer(String transfer);


    Transfer getTransfer(Long id);

    List<Transfer> getAllTransfers();
}
