package base.hotel_management.domain.services;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendBookingEmail(String to, byte[] pdf) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Booking Confirmation");
            helper.setText("Your booking has been confirmed. See attached PDF.");

            helper.addAttachment("booking.pdf",
                    new ByteArrayResource(pdf));

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Send mail failed", e);
        }
    }

    @Async
    public void sendUserEmail(String to, byte[] pdf) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("User Account Confirmation");
            helper.setText("Your account has been created. See attached PDF.");

            helper.addAttachment("UserAccount.pdf",
                    new ByteArrayResource(pdf));

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Send mail failed", e);
        }
    }
}