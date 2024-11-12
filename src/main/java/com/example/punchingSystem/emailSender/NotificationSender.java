package com.example.punchingSystem.emailSender;

import com.example.punchingSystem.dto.Defaulter;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotificationSender {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(NotificationSender.class);

    @Async
    public void sendNotification(Map<String, List<Defaulter>> defaulterMap) {
        defaulterMap.forEach((managerEmail, defaulterList) -> {
            System.out.println(managerEmail + " || " + defaulterList);
            System.out.println("Async Method called");

            try {
                // Create a MimeMessage
                MimeMessage mail = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mail, true);

                // Set the recipient, subject, and HTML content
                helper.setTo(managerEmail);
                helper.setSubject("Defaulters List:");

                // Build the email body with HTML content
                StringBuilder emailBody = new StringBuilder();
                emailBody.append("<html><body>");
                emailBody.append("<h3>Below are the defaulters for the day:-</h3>");
                emailBody.append("<table border='1' cellpadding='5' cellspacing='0' style='border-collapse: collapse;'>");
                emailBody.append("<tr><th>Email</th><th>Hours Logged</th></tr>");

                // Loop through the defaulter list and build table rows
                for (Defaulter defaulter : defaulterList) {
                    emailBody.append("<tr>");
                    emailBody.append("<td>").append(defaulter.getUserEmail()).append("</td>");
                    emailBody.append("<td>").append(defaulter.getHoursLogged()).append("</td>");
                    emailBody.append("</tr>");
                }

                emailBody.append("</table>");
                emailBody.append("</body></html>");

                // Set the HTML content as the body of the email
                helper.setText(emailBody.toString(), true);

                // Send the email
                mailSender.send(mail);
            } catch (Exception e) {
                logger.error("Failed to send email to {}", managerEmail, e); // Log the error with the manager's email
            }
        });
    }
}
