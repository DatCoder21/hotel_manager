package com.hotel_management.domain.entities;

import com.hotel_management.domain.enums.RoomCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private RoomCategory category;   // STANDARD / DELUXE / SUITE

    private Double price;

    private int maxRoomNumber;

    @OneToMany(mappedBy = "roomType")
    private List<Room> rooms;
}