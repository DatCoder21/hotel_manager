package com.hotel_management.app.requests.booking;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingRequest {

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String note;
    private Integer roomId;
}