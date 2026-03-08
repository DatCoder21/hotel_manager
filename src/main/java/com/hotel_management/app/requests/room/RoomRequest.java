package com.hotel_management.app.requests.room;

import lombok.Data;

@Data
public class RoomRequest {
    private String roomNumber;
    private String type;
    private Double price;
}