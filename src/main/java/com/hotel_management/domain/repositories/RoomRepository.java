package com.hotel_management.domain.repositories;

import com.hotel_management.domain.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}