package com.hotel_management.app.responses.room;

import com.hotel_management.domain.enums.RoomCategory;
import com.hotel_management.domain.enums.RoomStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoomResponse {
    private int id;
    private String roomNumber;
    private RoomStatus status;
    private RoomCategory category;
    private Double price;
}