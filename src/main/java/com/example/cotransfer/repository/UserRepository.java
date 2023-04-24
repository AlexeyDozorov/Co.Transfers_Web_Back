package com.example.cotransfer.repository;



import com.example.cotransfer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByIdentificationNumber(Long id);
    User findUsersByIdentificationNumber(Long identificationNumber);

    Optional<User> findUserByEmail(String email);
}
