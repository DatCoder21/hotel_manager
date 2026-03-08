package com.hotel_management.app.responses.room;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomResponse {
    private Long id;
    private String roomNumber;
    private String type;
    private Double price;
    private String status;
}