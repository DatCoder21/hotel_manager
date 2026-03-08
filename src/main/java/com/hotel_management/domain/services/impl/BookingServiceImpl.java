package com.hotel_management.domain.services.impl;

import com.hotel_management.app.requests.booking.BookingRequest;
import com.hotel_management.app.responses.booking.BookingResponse;
import com.hotel_management.domain.entities.Booking;
import com.hotel_management.domain.repositories.BookingRepository;
import com.hotel_management.domain.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        Booking booking = modelMapper.map(request, Booking.class);
        booking.setStatus("CONFIRMED");

        Booking saved = bookingRepository.save(booking);

        return modelMapper.map(saved, BookingResponse.class);
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(b -> modelMapper.map(b, BookingResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return modelMapper.map(booking, BookingResponse.class);
    }

    @Override
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }
}