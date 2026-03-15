package com.hotel_management.app.controllers.invoice;

import com.hotel_management.app.responses.invoiceItem.InvoiceResponse;
import com.hotel_management.domain.services.InvoiceManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice-management")
@RequiredArgsConstructor
public class InvoiceManagementController {

    private final InvoiceManagementService invoiceManagementService;

    // Xem hóa đơn theo booking
    @GetMapping("/booking/{bookingId}")
    public InvoiceResponse getInvoice(@PathVariable int bookingId) {
        return invoiceManagementService.getInvoiceByBooking(bookingId);
    }

    // Thanh toán hóa đơn
    @PostMapping("/{invoiceId}/pay")
    public void payInvoice(@PathVariable int invoiceId) {
        invoiceManagementService.payInvoice(invoiceId);
    }
}
