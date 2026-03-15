package com.hotel_management.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonIgnore
    private Invoice invoice;

    // Món nào
    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    // Số lượng đã gọi
    private Integer quantity;

    // Giá tại thời điểm đặt (lưu lịch sử)
    private Double unitPrice;

    // Thành tiền
    private Double subtotal;

}