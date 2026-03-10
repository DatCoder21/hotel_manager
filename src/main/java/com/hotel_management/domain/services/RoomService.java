package com.hotel_management.domain.services;

import com.hotel_management.app.requests.room.RoomRequest;
import com.hotel_management.app.responses.room.RoomResponse;

import java.util.List;

public interface RoomService {

    List<RoomResponse> getAllRooms();

    RoomResponse updateRoomStatus(Integer id, RoomRequest request);
}