package base.hotel_management.app.controllers.invoice;


import base.hotel_management.app.responses.invoiceItem.InvoiceItemResponse;
import base.hotel_management.domain.services.InvoiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@Tag(name = "Manage Invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    // Khách mua món (thêm vào hóa đơn)
    @PostMapping("/booking/{bookingId}/foods/{foodId}")
    public void addFoodToInvoice(
            @PathVariable int bookingId,
            @PathVariable int foodId,
            @RequestParam int quantity
    ) {
        invoiceService.addFoodToInvoice(bookingId, foodId, quantity);
    }

    // Lịch sử món đã mua theo booking
    @GetMapping("/booking/{bookingId}/history")
    public List<InvoiceItemResponse> getFoodHistory(
            @PathVariable int bookingId
    ) {
        return invoiceService.getFoodHistory(bookingId);
    }
}

