package com.example.cotransfer.model;


import com.example.cotransfer.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "arrival_date")
    private String arrivalDate;
    @Column(name = "arrival_time")
    private String arrivalTime;

    @Column(name = "flight_number")
    private  String flightNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "passport")
    private String passport;

    @Column(name = "telegram_login")
    private String telegramLogin;

    @Column(name = "trip_comment")
    private String tripComment;

    @Column(name = "identification_number")
    private Long identificationNumber;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinTable(
            name = "transfer_user",
            joinColumns = @JoinColumn(name = "transfer_id_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id_id")
    )
    private List<Transfer> transfer;
}
