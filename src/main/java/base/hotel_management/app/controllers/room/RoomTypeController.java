package base.hotel_management.app.controllers.room;

import base.hotel_management.app.requests.room.RoomTypeRequest;
import base.hotel_management.app.responses.room.RoomTypeResponse;
import base.hotel_management.domain.enums.RoomCategory;
import base.hotel_management.domain.services.RoomTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-types")
@Tag(name = "Manage Room Types")
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    // Chỉnh giá
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{category}/price")
    public RoomTypeResponse updatePrice(
            @PathVariable RoomCategory category,
            @RequestBody RoomTypeRequest request
    ) {
        return roomTypeService.updatePrice(category, request.getPrice());
    }

    // Xem theo loại phòng
    @GetMapping("/{category}")
    public List<RoomTypeResponse> getByCategory(
            @PathVariable RoomCategory category
    ) {
        return roomTypeService.getByCategory(category);
    }
}