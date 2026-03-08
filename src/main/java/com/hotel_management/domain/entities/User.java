package com.hotel_management.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role; // ADMIN, RECEPTIONIST, STAFF
}