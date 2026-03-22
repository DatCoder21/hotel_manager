package com.hotel_management.domain.repositories;

import com.hotel_management.domain.entities.Booking;
import com.hotel_management.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByGuest_Username(String username);

    // Kiểm tra trùng lịch đặt phòng (cho phép checkout A = checkin B)
    @Query("""
        SELECT b FROM Booking b
        WHERE b.room.id = :roomId
        AND b.status IN ('PENDING','CONFIRMED','PAID','CHECKED_IN')
        AND (
               :checkIn  < b.checkOutDate
           AND :checkOut > b.checkInDate
        )
    """)
    List<Booking> findConflictingBookings(
            @Param("roomId") Integer roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );

    // Booking đã hết hạn lưu trú (auto checkout)
    @Query("""
        SELECT b FROM Booking b
        WHERE b.status = 'CHECKED_IN'
        AND b.checkOutDate < :today
    """)
    List<Booking> findExpiredBookings(@Param("today") LocalDate today);
    List<Booking> findAll();
}