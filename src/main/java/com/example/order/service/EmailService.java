package com.example.order.service;

import com.example.order.configuration.EmailConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final EmailConfiguration emailConfiguration;

    @Value("${front.url}")
    private String frontendUrl;

    public void sendOrderMail(String email, String uid) {
        try {
            ClassPathResource resource = new ClassPathResource("static/email_order.html");
            InputStream inputStream = resource.getInputStream();
            String html = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();
            html = html.replace("https://google.com", frontendUrl + "/orders/" + uid);
            emailConfiguration.sendEmail(email, html, "Order has been created", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

