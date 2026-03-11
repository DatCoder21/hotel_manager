package com.hotel_management.domain.enums;

public enum BookingStatus {
    PENDING,     // Chờ xác nhận
    CONFIRMED,   // Đã xác nhận
    PAID,        // Đã thanh toán
    CHECKED_IN,  // Đã nhận phòng
    CHECKED_OUT, // Đã trả phòng
    CANCELLED    // Đã hủy
}