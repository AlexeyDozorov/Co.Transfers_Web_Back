package com.example.cotransfer.repository;



import com.example.cotransfer.model.Transfer;
import com.example.cotransfer.model.TransferUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface TransferUserRepository extends JpaRepository<TransferUser, Long> {

    TransferUser findByTransferId(Transfer transferId);
    List<TransferUser> findAllByTransferId(Transfer transferId);
    List<TransferUser> findAllByUserIdentificationNumber(Long userIdentificationNumber);
}
