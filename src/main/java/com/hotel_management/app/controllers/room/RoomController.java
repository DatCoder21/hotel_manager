package com.hotel_management.app.controllers.room;

import com.hotel_management.app.requests.room.RoomRequest;
import com.hotel_management.app.responses.room.RoomResponse;
import com.hotel_management.domain.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public RoomResponse create(@RequestBody RoomRequest request) {
        return roomService.createRoom(request);
    }

    @GetMapping
    public List<RoomResponse> getAll() {
        return roomService.getAllRooms();
    }
}