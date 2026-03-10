package com.hotel_management.domain.entities;

import com.hotel_management.domain.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String roomNumber;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;
}