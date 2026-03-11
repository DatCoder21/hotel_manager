package com.hotel_management.app.responses.booking;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Builder
@Data
public class BookingResponse {

    private Integer id;
    private String guestName;
    private Integer roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Double totalPrice;
    private String status;
    private String note;
}
