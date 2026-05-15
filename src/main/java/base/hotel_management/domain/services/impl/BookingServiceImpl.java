package base.hotel_management.domain.services.impl;

import base.hotel_management.app.requests.booking.BookingRequest;
import base.hotel_management.app.responses.booking.BookingResponse;
import base.hotel_management.domain.entities.Booking;
import base.hotel_management.domain.entities.Invoice;
import base.hotel_management.domain.entities.Room;
import base.hotel_management.domain.entities.User;
import base.hotel_management.domain.enums.BookingStatus;
import base.hotel_management.domain.enums.RoomStatus;
import base.hotel_management.domain.repositories.BookingRepository;
import base.hotel_management.domain.repositories.RoomRepository;
import base.hotel_management.domain.repositories.UserRepository;
import base.hotel_management.domain.services.BookingService;
import base.hotel_management.domain.services.EmailService;
import base.hotel_management.domain.services.PdfService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final PdfService pdfService;
    private final EmailService emailService;

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

        byte[] pdf = pdfService.generateBookingPdf(booking);

        emailService.sendBookingEmail(
                booking.getGuest().getEmail(),
                pdf
        );

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public BookingResponse StaffCreateBooking(BookingRequest request) {

        // Lấy user từ token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findById(request.getCustomerId())
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

        byte[] pdf = pdfService.generateBookingPdf(booking);

        emailService.sendBookingEmail(
                booking.getGuest().getEmail(),
                pdf
        );

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