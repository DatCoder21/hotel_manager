package base.hotel_management.domain.services;

import base.hotel_management.domain.entities.Booking;
import base.hotel_management.domain.entities.User;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generateBookingPdf(Booking booking) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            document.add(new Paragraph("BOOKING CONFIRMATION"));
            document.add(new Paragraph("----------------------------"));

            document.add(new Paragraph("Customer Name: " + booking.getGuest().getFullName()));
            document.add(new Paragraph("Customer Email: " + booking.getGuest().getEmail()));
            document.add(new Paragraph("Booking ID: " + booking.getId()));
            document.add(new Paragraph("Room: " + booking.getRoom().getRoomNumber()));
            document.add(new Paragraph("Check-in: " + booking.getCheckInDate()));
            document.add(new Paragraph("Check-out: " + booking.getCheckOutDate()));
            document.add(new Paragraph("Note: " + booking.getNote()));

            document.add(new Paragraph("Ordered: " + booking.getInvoice().getItems()));
            document.add(new Paragraph("Total Amount: " + booking.getTotalPrice()));

            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    public byte[] generateUserPdf(User user) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            document.add(new Paragraph("USER CONFIRMATION"));
            document.add(new Paragraph("----------------------------"));

            document.add(new Paragraph("Customer Name: " + user.getFullName()));
            document.add(new Paragraph("Email: " + user.getFullName()));
            document.add(new Paragraph("Phone Number: " + user.getPhone()));
            document.add(new Paragraph("" +
                                                "IF STAFF CREATED YOUR ACCOUNT. " +
                                                "YOUR PASSWORD IS SHOWED BELOW. " +
                                                "DO NOT SHARE YOUR PASSWORD!!!"));
            document.add(new Paragraph("Password: 123456"));

            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}