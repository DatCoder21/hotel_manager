package com.hotel_management.domain.repositories;

import com.hotel_management.domain.entities.Booking;
import com.hotel_management.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByGuest_Username(String username);
}