package base.hotel_management.domain.services.impl;

import base.hotel_management.app.responses.room.RoomTypeResponse;
import base.hotel_management.domain.entities.RoomType;
import base.hotel_management.domain.enums.RoomCategory;
import base.hotel_management.domain.repositories.RoomTypeRepository;
import base.hotel_management.domain.services.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;

    @Override
    // 🎯 Chỉnh giá loại phòng
    public RoomTypeResponse updatePrice(RoomCategory category, Double newPrice) {

        RoomType roomType = roomTypeRepository.findByCategory(category)
                .orElseThrow(() -> new RuntimeException("Room type not found"));

        roomType.setPrice(newPrice);
        roomTypeRepository.save(roomType);

        return mapToResponse(roomType);
    }

    @Override
    // 🎯 Xem danh sách theo loại phòng
    public List<RoomTypeResponse> getByCategory(RoomCategory category) {

        return roomTypeRepository.findAllByCategory(category)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public RoomTypeResponse mapToResponse(RoomType rt) {
        return RoomTypeResponse.builder()
                .id(rt.getId())
                .category(rt.getCategory())
                .price(rt.getPrice())
                .maxRoomNumber(rt.getMaxRoomNumber())
                .build();
    }
}
