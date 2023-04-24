package com.example.cotransfer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "transfer_user")
@Getter
@Setter
@NoArgsConstructor
public class TransferUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_identification_number")
    private Long userIdentificationNumber;

    @ManyToOne
    private User userId;

    @ManyToOne
    private Transfer transferId;
}
