package base.hotel_management.domain.services;

import base.hotel_management.app.responses.invoiceItem.InvoiceItemResponse;
import base.hotel_management.app.responses.invoiceItem.InvoiceResponse;

import java.util.List;

public interface InvoiceService {
    void addFoodToInvoice(int bookingId, int foodId, int quantity);
    List<InvoiceItemResponse> getFoodHistory(int bookingId);
    InvoiceResponse getInvoiceByBooking(int bookingId);
}