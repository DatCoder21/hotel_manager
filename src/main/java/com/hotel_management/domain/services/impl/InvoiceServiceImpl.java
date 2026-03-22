package com.hotel_management.domain.services.impl;

import com.hotel_management.app.responses.invoiceItem.InvoiceItemResponse;
import com.hotel_management.app.responses.invoiceItem.InvoiceResponse;
import com.hotel_management.domain.entities.Booking;
import com.hotel_management.domain.entities.Food;
import com.hotel_management.domain.entities.Invoice;
import com.hotel_management.domain.entities.InvoiceItem;
import com.hotel_management.domain.repositories.BookingRepository;
import com.hotel_management.domain.repositories.FoodRepository;
import com.hotel_management.domain.repositories.InvoiceItemRepository;
import com.hotel_management.domain.repositories.InvoiceRepository;
import com.hotel_management.domain.services.InvoiceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final BookingRepository bookingRepository;
    private final FoodRepository foodRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final ModelMapper modelMapper;


    // Khách mua món
    @Transactional
    @Override
    public void addFoodToInvoice(int bookingId, int foodId, int quantity) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Invoice invoice = booking.getInvoice();

        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        if (food.getNumber() < quantity) {
            throw new RuntimeException("Not enough food in stock");
        }

        // Trừ tồn kho
        food.setNumber(food.getNumber() - quantity);
        foodRepository.save(food);

        // Thêm món vào hóa đơn
        InvoiceItem item = new InvoiceItem();
        item.setInvoice(invoice);
        item.setFood(food);
        item.setQuantity(quantity);
        item.setUnitPrice(food.getPrice());

        double subtotal = food.getPrice() * quantity;
        item.setSubtotal(subtotal);

        invoiceItemRepository.save(item);

        // ✅ CỘNG TIỀN VÀO HÓA ĐƠN
        if (invoice.getTotalAmount() == null) {
            invoice.setTotalAmount(0.0);
        }
        invoice.setTotalAmount(invoice.getTotalAmount() + subtotal);
        booking.setTotalPrice(booking.getTotalPrice() + item.getSubtotal());
        bookingRepository.saveAndFlush(booking);
        invoiceRepository.save(invoice);
    }



    // 📜 Lịch sử món đã mua theo booking
    @Override
    public List<InvoiceItemResponse> getFoodHistory(int bookingId) {

        return invoiceItemRepository.findAllByInvoice_Booking_Id(bookingId)
                .stream()
                .map(i -> new InvoiceItemResponse(
                        i.getFood().getFoodName(),
                        i.getQuantity(),
                        i.getUnitPrice(),
                        i.getSubtotal()
                ))
                .toList();
    }
    //Xem hóa đơn theo Booking
    @Override
    public InvoiceResponse getInvoiceByBooking(int bookingId) {

        Invoice invoice = invoiceRepository
                .findByBooking_Id(bookingId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        return mapToResponse(invoice);
    }

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
                .collect(Collectors.toList());

        res.setItems(items);

        return res;
    }
}