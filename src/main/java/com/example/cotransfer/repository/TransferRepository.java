package com.example.cotransfer.repository;


import com.example.cotransfer.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findAllById(Long id);
    List<Transfer>  findAllByIsShared(boolean flag);
    List<Transfer> findAllByStartLocationAndEndLocation(String startLocation, String endLocation);
    Optional<Transfer> findByIdAndIsShared(Long id, boolean b);
}
