package com.hotel_management.app.controllers.room;

import com.hotel_management.app.requests.room.RoomRequest;
import com.hotel_management.app.responses.room.RoomResponse;
import com.hotel_management.domain.enums.RoomCategory;
import com.hotel_management.domain.enums.RoomStatus;
import com.hotel_management.domain.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    // Lấy tất cả phòng
    @GetMapping
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms();
    }

    // Cập nhật trạng thái phòng
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @PutMapping("/{id}/status")
    public RoomResponse updateStatus(
            @PathVariable Integer id,
            @RequestParam RoomStatus status
    ) {
        return roomService.updateRoomStatus(id, status);
    }

    //Tim phong trong theo ngay
    @GetMapping("/available")
    public List<RoomResponse> getAvailableRooms(
            @RequestParam RoomCategory category,
            @RequestParam String checkIn,
            @RequestParam String checkOut
    ) {
        return roomService.findAvailableRooms(
                category,
                LocalDate.parse(checkIn),
                LocalDate.parse(checkOut)
        );
    }
}