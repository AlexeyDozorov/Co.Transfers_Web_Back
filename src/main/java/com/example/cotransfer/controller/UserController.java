package com.example.cotransfer.controller;

import com.example.cotransfer.model.Transfer;
import com.example.cotransfer.model.User;
import com.example.cotransfer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/all-transfers")
    @PreAuthorize("hasRole('USER')")
    public Set<Transfer> getAllUserTransfers(){
        return userService.getAllUserTransfers();
    }

//    @GetMapping("/allTransfers")
//    private Page<Transfer> getAllTransfers(@PageableDefault(size = 125) Pageable pageable, @RequestHeader(name = "id") Long id ) {
//        Page<Transfer> allUserTransfers = userService.getAllUserTransfers(id, pageable);
//        return allUserTransfers;
//    }

}
