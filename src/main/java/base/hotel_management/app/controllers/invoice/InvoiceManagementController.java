package base.hotel_management.app.controllers.invoice;

import base.hotel_management.app.responses.invoiceItem.InvoiceResponse;
import base.hotel_management.domain.services.InvoiceManagementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice-management")
@Tag(name = "Payment Invoice")
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
