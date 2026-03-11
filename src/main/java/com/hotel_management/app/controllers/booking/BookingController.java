package com.hotel_management.app.controllers.booking;

import com.hotel_management.app.requests.booking.BookingRequest;
import com.hotel_management.app.responses.booking.BookingResponse;
import com.hotel_management.domain.enums.BookingStatus;
import com.hotel_management.domain.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // ✅ User đặt phòng
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping
    public BookingResponse create(@RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }

    // ✅ User xem booking của mình
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @GetMapping("/my")
    public List<BookingResponse> myBookings() {
        return bookingService.getMyBookings();
    }

    // ✅ Admin cập nhật trạng thái booking
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @PutMapping("/{id}/status")
    public BookingResponse updateStatus(
            @PathVariable Integer id,
            @RequestParam BookingStatus status
    ) {
        return bookingService.updateBookingStatus(id, status);
    }
}