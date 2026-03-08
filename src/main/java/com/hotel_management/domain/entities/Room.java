package com.hotel_management.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;
    private String type;     // STANDARD, DELUXE
    private Double price;
    private String status;   // AVAILABLE, OCCUPIED
}