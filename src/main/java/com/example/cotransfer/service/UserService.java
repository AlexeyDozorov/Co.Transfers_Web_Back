package com.example.cotransfer.service;


import com.example.cotransfer.model.Transfer;
import com.example.cotransfer.model.User;
import org.jvnet.hk2.annotations.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;
@Service
public interface UserService {

    List<User> getAllUsers();


    User getUser(Long id);

    User getUserByEmail(String email);

    void save(User user);


    void deleteUser(Long id);

    void update(User user);

    Set<Transfer> getAllUserTransfers();

}
