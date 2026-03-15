package com.hotel_management.domain.services.impl;

import com.hotel_management.app.responses.invoiceItem.InvoiceItemResponse;
import com.hotel_management.app.responses.invoiceItem.InvoiceResponse;
import com.hotel_management.domain.entities.Booking;
import com.hotel_management.domain.entities.Invoice;
import com.hotel_management.domain.enums.BookingStatus;
import com.hotel_management.domain.repositories.BookingRepository;
import com.hotel_management.domain.repositories.InvoiceRepository;
import com.hotel_management.domain.services.InvoiceManagementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import com.hotel_management.domain.entities.InvoiceItem;

@Service
@RequiredArgsConstructor
public class InvoiceManagementServiceImpl implements InvoiceManagementService {

    private final InvoiceRepository invoiceRepository;
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    // ==============================
    // 🔍 XEM HÓA ĐƠN
    // ==============================
    @Override
    public InvoiceResponse getInvoiceByBooking(int bookingId) {

        Invoice invoice = invoiceRepository
                .findByBooking_Id(bookingId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        double total = calculateTotal(invoice);
        invoice.setTotalAmount(total);
        invoiceRepository.save(invoice);

        return mapToResponse(invoice);
    }

    // ==============================
    // 💳 THANH TOÁN
    // ==============================
    @Transactional
    @Override
    public void payInvoice(int invoiceId) {

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Booking booking = invoice.getBooking();

        if (booking.getStatus() == BookingStatus.PAID) {
            throw new RuntimeException("Invoice already paid");
        }

        double total = calculateTotal(invoice);
        invoice.setTotalAmount(total);

        booking.setStatus(BookingStatus.PAID);

        bookingRepository.save(booking);
        invoiceRepository.save(invoice);
    }

    // ==============================
    // 🧮 TÍNH TỔNG
    // ==============================
    private double calculateTotal(Invoice invoice) {

        double foodTotal = invoice.getItems()
                .stream()
                .mapToDouble(InvoiceItem::getSubtotal)
                .sum();

        double roomTotal = invoice.getBooking().getTotalPrice();

        return foodTotal + roomTotal;
    }

    // ==============================
    // 🔄 MAP RESPONSE
    // ==============================
    private InvoiceResponse mapToResponse(Invoice invoice) {

        InvoiceResponse res = modelMapper.map(invoice, InvoiceResponse.class);

        res.setInvoiceId(invoice.getId());
        res.setBookingId(invoice.getBooking().getId());

        List<InvoiceItemResponse> items = invoice.getItems()
                .stream()
                .map(item -> {
                    InvoiceItemResponse r = modelMapper.map(item, InvoiceItemResponse.class);
                    r.setFoodName(item.getFood().getFoodName());
                    return r;
                })
                .toList();

        res.setItems(items);
        return res;
    }
}
