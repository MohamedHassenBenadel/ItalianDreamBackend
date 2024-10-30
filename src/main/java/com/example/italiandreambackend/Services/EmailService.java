package com.example.italiandreambackend.Services;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendHTMLEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart message

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // true indicates body is HTML
        mailSender.send(message);
    }
    public void sendWelcomeEmail(String to, String clientId, String password) {
        String subject = "Welcome to Italian Dream!";
        String body = String.format(
                "<html>" +
                        "<head>" +
                        "<style>" +
                        "body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }" +
                        ".container { max-width: 600px; margin: auto; background: white; border-radius: 8px; padding: 20px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }" +
                        ".header { text-align: center; color: #007BFF; }" +
                        ".footer { margin-top: 20px; text-align: center; font-size: 0.9em; color: #666; }" +
                        "h1 { font-size: 24px; }" +
                        "p { font-size: 16px; line-height: 1.5; }" +
                        ".highlight { font-weight: bold; color: #007BFF; }" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<div class='container'>" +
                        "<h1 class='header'>Welcome to Italian Dream!</h1>" +
                        "<p>Cher Client,</p>" +
                        "<p>Nous sommes ravis de vous accueillir dans la famille d'Italian Dream !</p>" +
                        "<p>Voici vos informations :</p>" +
                        "<p><span class='highlight'>Votre Client ID:</span> %s</p>" +
                        "<p><span class='highlight'>Votre mot de passe:</span> %s</p>" +
                        "<p>Nous espérons que vous apprécierez notre service et que vous passerez un merveilleux moment avec nous.</p>" +
                        "<p>Si vous avez des questions, n'hésitez pas à nous contacter.</p>" +
                        "<div class='footer'>Cordialement,<br>L'équipe d'Italian Dream</div>" +
                        "</div>" +
                        "</body>" +
                        "</html>",
                clientId, password
        );

        try {
            sendHTMLEmail(to, subject, body); // Use sendHTMLEmail for HTML content
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle exception as needed
        }
    }

}
