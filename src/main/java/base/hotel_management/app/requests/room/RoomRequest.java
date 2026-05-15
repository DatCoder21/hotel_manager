package base.hotel_management.app.requests.room;

import base.hotel_management.domain.enums.RoomStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequest {
    private RoomStatus status;
}