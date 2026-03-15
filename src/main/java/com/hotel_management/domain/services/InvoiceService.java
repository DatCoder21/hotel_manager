package com.hotel_management.domain.services;

import com.hotel_management.app.responses.invoiceItem.InvoiceItemResponse;
import com.hotel_management.app.responses.invoiceItem.InvoiceResponse;

import java.util.List;

public interface InvoiceService {
    void addFoodToInvoice(int bookingId, int foodId, int quantity);
    List<InvoiceItemResponse> getFoodHistory(int bookingId);
    InvoiceResponse getInvoiceByBooking(int bookingId);
}