package com.hotel_management.domain.entities;

import com.hotel_management.domain.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    private String note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User guest;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}