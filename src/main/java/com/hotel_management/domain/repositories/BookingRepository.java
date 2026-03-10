package com.hotel_management.domain.repositories;

import com.hotel_management.domain.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}