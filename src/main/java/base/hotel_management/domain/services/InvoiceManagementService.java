package base.hotel_management.domain.services;

import base.hotel_management.app.responses.invoiceItem.InvoiceResponse;

public interface InvoiceManagementService {

    InvoiceResponse getInvoiceByBooking(int bookingId);

    void payInvoice(int invoiceId);
}