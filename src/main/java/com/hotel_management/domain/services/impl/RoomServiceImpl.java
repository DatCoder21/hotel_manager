package com.hotel_management.domain.services.impl;

import com.hotel_management.app.requests.room.RoomRequest;
import com.hotel_management.app.responses.room.RoomResponse;
import com.hotel_management.domain.entities.Room;
import com.hotel_management.domain.repositories.RoomRepository;
import com.hotel_management.domain.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Override
    public RoomResponse createRoom(RoomRequest request) {
        Room room = modelMapper.map(request, Room.class);
        room.setStatus("AVAILABLE");
        Room saved = roomRepository.save(room);
        return modelMapper.map(saved, RoomResponse.class);
    }

    @Override
    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(r -> modelMapper.map(r, RoomResponse.class))
                .collect(Collectors.toList());
    }
}
