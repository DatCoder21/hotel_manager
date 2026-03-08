package com.hotel_management.domain.services;

import com.hotel_management.app.requests.booking.BookingRequest;
import com.hotel_management.app.responses.booking.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    List<BookingResponse> getAllBookings();
    BookingResponse getBookingById(Long id);
    void cancelBooking(Long id);
}