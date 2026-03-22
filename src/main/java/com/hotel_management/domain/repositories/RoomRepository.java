package com.hotel_management.domain.repositories;

import com.hotel_management.domain.entities.Room;
import com.hotel_management.domain.enums.RoomCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("""
    SELECT r FROM Room r
    WHERE r.roomType.category = :category
    AND r.status = 'AVAILABLE'
    AND r.id NOT IN (
        SELECT b.room.id FROM Booking b
        WHERE b.status != 'CANCELLED'
        AND (
            :checkIn < b.checkOutDate AND :checkOut > b.checkInDate
        )
    )
""")
    List<Room> findAvailableRooms(
            @Param("category") RoomCategory category,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );
}