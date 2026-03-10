package com.hotel_management.app.controllers.room;

import com.hotel_management.app.requests.room.RoomTypeRequest;
import com.hotel_management.app.responses.room.RoomTypeResponse;
import com.hotel_management.domain.enums.RoomCategory;
import com.hotel_management.domain.services.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-types")
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    // ✅ Chỉnh giá
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{category}/price")
    public RoomTypeResponse updatePrice(
            @PathVariable RoomCategory category,
            @RequestBody RoomTypeRequest request
    ) {
        return roomTypeService.updatePrice(category, request.getPrice());
    }

    // ✅ Xem theo loại phòng
    @GetMapping("/{category}")
    public List<RoomTypeResponse> getByCategory(
            @PathVariable RoomCategory category
    ) {
        return roomTypeService.getByCategory(category);
    }
}