package base.hotel_management.domain.repositories;

import base.hotel_management.domain.entities.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem,Integer> {
    List<InvoiceItem> findAllByInvoice_Booking_Id(int bookingId);
}
