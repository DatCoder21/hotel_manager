package com.hotel_management.domain.services.impl;

import com.hotel_management.app.requests.booking.BookingRequest;
import com.hotel_management.app.responses.booking.BookingResponse;
import com.hotel_management.domain.entities.Booking;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

        // 🔹 Lấy user từ token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔹 Lấy phòng
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // 🔹 Kiểm tra phòng trống
        if (room.getStatus() != RoomStatus.AVAILABLE) {
            throw new RuntimeException("Room is not available");
        }

        // 🔹 Tính tiền
        long days = ChronoUnit.DAYS.between(
                request.getCheckInDate(),
                request.getCheckOutDate()
        );

        double totalPrice = days * room.getRoomType().getPrice();

        // 🔹 Tạo booking
        Booking booking = new Booking();
        booking.setGuest(user);
        booking.setRoom(room);
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setNote(request.getNote());
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatus.PENDING);

        bookingRepository.save(booking);

        // 🔥 Quan trọng: đổi trạng thái phòng
        room.setStatus(RoomStatus.OCCUPIED);
        roomRepository.save(room);

        return mapToResponse(booking);
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

        // ✅ Nếu khách trả phòng → phòng trống lại
        if (status == BookingStatus.CHECKED_OUT || status == BookingStatus.CANCELLED) {
            Room room = booking.getRoom();
            room.setStatus(RoomStatus.AVAILABLE);
            roomRepository.save(room);
        }

        bookingRepository.save(booking);

        return mapToResponse(booking);
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
}