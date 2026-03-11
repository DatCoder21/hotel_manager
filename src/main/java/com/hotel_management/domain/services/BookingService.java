package com.hotel_management.domain.services;

import com.hotel_management.app.requests.booking.BookingRequest;
import com.hotel_management.app.responses.booking.BookingResponse;
import com.hotel_management.domain.enums.BookingStatus;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    BookingResponse updateBookingStatus(Integer bookingId, BookingStatus status);
    List<BookingResponse> getMyBookings();
}