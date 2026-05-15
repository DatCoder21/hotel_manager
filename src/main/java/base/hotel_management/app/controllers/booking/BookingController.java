package base.hotel_management.app.controllers.booking;

import base.hotel_management.app.requests.booking.BookingRequest;
import base.hotel_management.app.responses.booking.BookingResponse;
import base.hotel_management.domain.enums.BookingStatus;
import base.hotel_management.domain.services.BookingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Manage Booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // User đặt phòng
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping
    public BookingResponse create(@RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @PostMapping("/staffCreate")
    public BookingResponse create2(@RequestBody BookingRequest request) {
        return bookingService.StaffCreateBooking(request);
    }

    // User xem booking của mình
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @GetMapping("/my")
    public List<BookingResponse> myBookings() {
        return bookingService.getMyBookings();
    }

    // Admin cập nhật trạng thái booking
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @PutMapping("/{id}/status")
    public BookingResponse updateStatus(
            @PathVariable Integer id,
            @RequestParam BookingStatus status
    ) {
        return bookingService.updateBookingStatus(id, status);
    }

    //Lay danh sach tat ca cac booking
    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public List<BookingResponse> getAllBookings() {
        return bookingService.getAllBookings();
    }
}