package com.complex.finAdvisor.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender mailSender;

    public void sendSimpleMessage(String to, String subject, String body) {
        SimpleMailMessage simple = new SimpleMailMessage();
        simple.setTo(to);
        simple.setSubject(subject);
        simple.setText(body);

        mailSender.send(simple);
    }
    public void sendLetter(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true для поддержки вложений
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // true означает, что это HTML
        mailSender.send(message);
    }

    public void sendResetCodeHtmlMessage(String to, String subject, String resetCode) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);

        // Используем InputStream для загрузки HTML-шаблона
        try (InputStream inputStream = new ClassPathResource("templates/resetPasswordLetter.html").getInputStream()) {
            String htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Замена плейсхолдеров на динамический контент
            htmlContent = htmlContent.replace("{userEmail}", to);
            htmlContent = htmlContent.replace("{resetCode}", resetCode);

            helper.setText(htmlContent, true); // true - для отправки как HTML
        }

        mailSender.send(message);
    }
    public void sendWelcomeHtmlMessage(String to, String subject) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);

        // Используем InputStream для загрузки HTML-шаблона
        try (InputStream inputStream = new ClassPathResource("templates/welcomeLetter.html").getInputStream()) {
            String htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Замена плейсхолдеров на динамический контент
            htmlContent = htmlContent.replace("{userEmail}", to);

            helper.setText(htmlContent, true); // true - для отправки как HTML
        }

        mailSender.send(message);
    }
}
