package com.hotel_management.app.requests.room;

import com.hotel_management.domain.enums.RoomStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequest {
    private RoomStatus status;
}