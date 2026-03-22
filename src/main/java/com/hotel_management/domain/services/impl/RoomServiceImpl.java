package com.hotel_management.domain.services.impl;

import com.hotel_management.app.requests.room.RoomRequest;
import com.hotel_management.app.responses.room.RoomResponse;
import com.hotel_management.domain.entities.Room;
import com.hotel_management.domain.enums.RoomCategory;
import com.hotel_management.domain.enums.RoomStatus;
import com.hotel_management.domain.repositories.RoomRepository;
import com.hotel_management.domain.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

//    @Override
//    public RoomResponse updateRoomStatus(Integer id, RoomStatus status) {
//
//        Room room = roomRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Room not found"));
//
//        room.setStatus(status);
//
//        roomRepository.save(room);
//
//        return mapToResponse(room);
//    }
//
//    private RoomResponse mapToResponse(Room r) {
//        return RoomResponse.builder()
//                .id(r.getId())
//                .roomNumber(r.getRoomNumber())
//                .status(r.getStatus())
//                .category(r.getRoomType().getCategory())
//                .price(r.getRoomType().getPrice())
//                .build();
//    }

    @Override
    public RoomResponse updateRoomStatus(Integer id, RoomStatus status) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setStatus(status);
        roomRepository.save(room);

        return mapToResponse(room);
    }

    private RoomResponse mapToResponse(Room r) {
        return RoomResponse.builder()
                .id(r.getId())
                .roomNumber(r.getRoomNumber())
                .status(r.getStatus())
                .category(r.getRoomType().getCategory())
                .price(r.getRoomType().getPrice())
                .build();
    }

    @Override
    public List<RoomResponse> findAvailableRooms(
            RoomCategory category,
            LocalDate checkIn,
            LocalDate checkOut
    ) {
        return roomRepository.findAvailableRooms(category, checkIn, checkOut)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}