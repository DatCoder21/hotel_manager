package com.hotel_management.app.responses.room;

import com.hotel_management.domain.enums.RoomCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoomTypeResponse {
    private int id;
    private RoomCategory category;
    private Double price;
    private int maxRoomNumber;
}