package com.hotel_management.app.responses.booking;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class BookingResponse {
    private Long bookingId;
    private String guestName;
    private String roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Double totalPrice;
    private String status;
}
