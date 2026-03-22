package com.hotel_management.domain.services;

import com.hotel_management.app.requests.room.RoomRequest;
import com.hotel_management.app.responses.room.RoomResponse;
import com.hotel_management.domain.enums.RoomCategory;
import com.hotel_management.domain.enums.RoomStatus;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    List<RoomResponse> getAllRooms();

    RoomResponse updateRoomStatus(Integer id, RoomStatus status);

    List<RoomResponse> findAvailableRooms(
            RoomCategory category,
            LocalDate checkIn,
            LocalDate checkOut
    );
}