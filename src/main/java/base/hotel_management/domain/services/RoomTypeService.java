package base.hotel_management.domain.services;

import base.hotel_management.app.responses.room.RoomTypeResponse;
import base.hotel_management.domain.entities.RoomType;
import base.hotel_management.domain.enums.RoomCategory;

import java.util.List;

public interface RoomTypeService {

    public RoomTypeResponse updatePrice(RoomCategory category, Double newPrice);
    public List<RoomTypeResponse> getByCategory(RoomCategory category) ;
    public RoomTypeResponse mapToResponse(RoomType rt);
    }
