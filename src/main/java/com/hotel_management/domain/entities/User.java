package com.hotel_management.domain.entities;

import com.hotel_management.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String password;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.CUSTOMER;

    @OneToMany(mappedBy = "guest")
    private List<Booking> bookings;
}