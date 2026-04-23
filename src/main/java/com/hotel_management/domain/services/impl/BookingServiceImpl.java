package com.hotel_management.domain.services.impl;

import com.hotel_management.app.requests.booking.BookingRequest;
import com.hotel_management.app.responses.booking.BookingResponse;
import com.hotel_management.domain.entities.Booking;
import com.hotel_management.domain.entities.Invoice;
import com.hotel_management.domain.entities.Room;
import com.hotel_management.domain.entities.User;
import com.hotel_management.domain.enums.BookingStatus;
import com.hotel_management.domain.enums.RoomStatus;
import com.hotel_management.domain.repositories.BookingRepository;
import com.hotel_management.domain.repositories.RoomRepository;
import com.hotel_management.domain.repositories.UserRepository;
import com.hotel_management.domain.services.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {

        // Lấy user từ token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Lấy phòng
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        LocalDate checkIn = request.getCheckInDate();
        LocalDate checkOut = request.getCheckOutDate();

        // Validate ngày
        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (days <= 0) {
            throw new RuntimeException("Check-out date must be after check-in date");
        }

        // Check trùng lịch
        List<Booking> conflicts = bookingRepository.findConflictingBookings(
                room.getId(), checkIn, checkOut
        );

        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Room already booked for selected dates");
        }

        if (room.getStatus() != RoomStatus.AVAILABLE) {
            throw new RuntimeException("Room is not available");
        }

        // Tính tiền theo số đêm
        double totalPrice = days * room.getRoomType().getPrice();

        // Tạo booking
        Booking booking = new Booking();
        booking.setGuest(user);
        booking.setRoom(room);
        booking.setCheckInDate(checkIn);
        booking.setCheckOutDate(checkOut);
        booking.setNote(request.getNote());
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatus.PENDING);

        // Tạo hóa đơn
        Invoice invoice = new Invoice();
        invoice.setBooking(booking);
        booking.setInvoice(invoice);

        Booking saved = bookingRepository.save(booking);

        return mapToResponse(saved);
    }


    private BookingResponse mapToResponse(Booking b) {
        return BookingResponse.builder()
                .id(b.getId())
                .guestName(b.getGuest().getFullName())
                .roomId(b.getRoom().getId())
                .checkInDate(b.getCheckInDate())
                .checkOutDate(b.getCheckOutDate())
                .totalPrice(b.getTotalPrice())
                .status(String.valueOf(b.getStatus()))
                .note(b.getNote())
                .build();
    }

    @Override
    @Transactional
    public BookingResponse updateBookingStatus(Integer bookingId, BookingStatus status) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(status);

        if (status == BookingStatus.CHECKED_OUT || status == BookingStatus.CANCELLED) {
            Room room = booking.getRoom();
            room.setStatus(RoomStatus.AVAILABLE);
            roomRepository.save(room);
        }

        bookingRepository.save(booking);
        return mapToResponse(booking);
    }

//    @Override
//    @Transactional
//    public BookingResponse customerCheckIn(Integer bookingId) {
//
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new RuntimeException("Booking not found"));
//
//        booking.setStatus(BookingStatus.CHECKED_IN);
//
//        Room room = booking.getRoom();
//        roomRepository.save(room);
//
//        bookingRepository.save(booking);
//
//        return mapToResponse(booking);
//    }

    @Override
    @Transactional
    public BookingResponse customerCheckIn(Integer bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));

        // Validate thời gian check-in
        if (today.isBefore(booking.getCheckInDate())) {
            throw new RuntimeException("The check-in date has not yet arrived.");
        }

        if (today.isAfter(booking.getCheckOutDate())) {
            throw new RuntimeException("The booking has expired, check-in is not possible.");
        }

        // Nếu hợp lệ thì check-in
        booking.setStatus(BookingStatus.CHECKED_IN);

        Room room = booking.getRoom();
        roomRepository.save(room);

        bookingRepository.save(booking);

        return mapToResponse(booking);
    }

    @Override
    @Transactional
    public BookingResponse customerCheckOut(Integer bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if(booking.getStatus() == BookingStatus.PAID){
            booking.setStatus(BookingStatus.CHECKED_OUT);
            Room room = booking.getRoom();
            room.setStatus(RoomStatus.AVAILABLE);
            roomRepository.save(room);

            bookingRepository.save(booking);

            return mapToResponse(booking);
        }
        else {
            throw new RuntimeException("Booking is not Paid, can not checkout");
        }
    }

    @Override
    public List<BookingResponse> getMyBookings() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return bookingRepository.findAllByGuest_Username(username)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // AUTO CHECKOUT WHEN EXPIRE
    @Scheduled(cron = "0 0 0 * * ?") // chạy 00:00 mỗi ngày
    @Transactional
    public void autoCheckoutExpiredBookings() {

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));

        List<Booking> expired = bookingRepository.findExpiredBookings(today);

        for (Booking b : expired) {
            b.setStatus(BookingStatus.CHECKED_OUT);

            Room room = b.getRoom();
            room.setStatus(RoomStatus.AVAILABLE);
        }
    }
}