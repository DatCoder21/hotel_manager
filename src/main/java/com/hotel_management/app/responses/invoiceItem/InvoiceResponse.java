package com.hotel_management.app.responses.invoiceItem;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponse {

    private Integer invoiceId;

    private Integer bookingId;

    private Double totalAmount;

    private String status;

    // Danh sách món trong hóa đơn
    private List<InvoiceItemResponse> items;
}
