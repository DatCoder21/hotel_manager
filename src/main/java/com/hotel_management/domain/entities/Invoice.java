package com.hotel_management.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 1 Invoice thuộc 1 Booking
    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    // Danh sách món đã gọi
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceItem> items = new ArrayList<>();

    // Tổng tiền cần thanh toán
    private Double totalAmount;
}