package base.hotel_management.domain.services;

import base.hotel_management.app.requests.booking.BookingRequest;
import base.hotel_management.app.responses.booking.BookingResponse;
import base.hotel_management.domain.enums.BookingStatus;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    BookingResponse StaffCreateBooking(BookingRequest request);
    BookingResponse updateBookingStatus(Integer bookingId, BookingStatus status);
    List<BookingResponse> getMyBookings();
    List<BookingResponse> getAllBookings();
}