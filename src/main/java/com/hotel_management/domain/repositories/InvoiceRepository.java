package com.hotel_management.domain.repositories;

import com.hotel_management.domain.entities.Booking;
import com.hotel_management.domain.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Optional<Invoice> findByBooking_Id(int bookingId);
    Optional<Invoice> findByBooking(Booking booking);

}
