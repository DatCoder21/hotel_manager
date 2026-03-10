package com.hotel_management.app.controllers.room;

import com.hotel_management.app.requests.room.RoomRequest;
import com.hotel_management.app.responses.room.RoomResponse;
import com.hotel_management.domain.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    // ✅ Lấy tất cả phòng
    @GetMapping
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms();
    }

    // ✅ Cập nhật trạng thái phòng
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @PutMapping("/{id}/status")
    public RoomResponse updateStatus(
            @PathVariable Integer id,
            @RequestBody RoomRequest request
    ) {
        return roomService.updateRoomStatus(id, request);
    }
}