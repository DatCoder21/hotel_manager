//package com.hotel_management.app.controllers.booking;
//
//import com.hotel_management.app.requests.booking.BookingRequest;
//import com.hotel_management.app.responses.booking.BookingResponse;
//import com.hotel_management.domain.services.BookingService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/bookings")
//@RequiredArgsConstructor
//public class BookingController {
//
//    private final BookingService bookingService;
//
//    @PostMapping
//    public BookingResponse create(@RequestBody BookingRequest request) {
//        return bookingService.createBooking(request);
//    }
//
//    @GetMapping
//    public List<BookingResponse> getAll() {
//        return bookingService.getAllBookings();
//    }
//
//    @GetMapping("/{id}")
//    public BookingResponse getById(@PathVariable Long id) {
//        return bookingService.getBookingById(id);
//    }
//
//    @PutMapping("/{id}/cancel")
//    public void cancel(@PathVariable Long id) {
//        bookingService.cancelBooking(id);
//    }
//}
