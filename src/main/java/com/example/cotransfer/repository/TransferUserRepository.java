package com.example.cotransfer.repository;



import com.example.cotransfer.model.Transfer;
import com.example.cotransfer.model.TransferUser;
import com.example.cotransfer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface TransferUserRepository extends JpaRepository<TransferUser, Long> {


    List<TransferUser> findAllByTransferId(Transfer transferId);

    List<TransferUser> findAllByUserId(User user);
}
