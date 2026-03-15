package com.hotel_management.app.responses.invoiceItem;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItemResponse {

    private String foodName;   // Tên món
    private Integer quantity; // Số lượng mua
    private Double unitPrice; // Giá tại thời điểm mua
    private Double subtotal;  // Thành tiền
}