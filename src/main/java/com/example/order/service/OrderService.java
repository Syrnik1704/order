package com.example.order.service;

import com.example.order.entity.*;
import com.example.order.mappers.BasketItemDTOToOrderItemsMapper;
import com.example.order.repository.DeliverRepository;
import com.example.order.repository.OrderRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeliverRepository deliverRepository;
    private final BasketService basketService;
    private final ItemService itemService;
    private final PayUService payUService;
    private final EmailService emailService;
    private final AuthService authService;
    private final BasketItemDTOToOrderItemsMapper basketItemDTOToOrderItemsMapper;


    private Order save(Order order) {
        Deliver deliver = deliverRepository.findByUid(order.getDeliver().getUid()).orElseThrow(RuntimeException::new);
        StringBuilder stringBuilder = new StringBuilder("ORDER/")
                .append(orderRepository.count())
                .append("/")
                .append(LocalDate.now().getMonthValue())
                .append("/")
                .append(LocalDate.now().getYear());

        order.setUid(UUID.randomUUID().toString());
        order.setStatus(Status.PENDING);
        order.setOrders(stringBuilder.toString());
        order.setDeliver(deliver);
        return orderRepository.saveAndFlush(order);
    }

    public String createOrder(Order order, HttpServletRequest request, HttpServletResponse response) {
        List<Cookie> cookies = Arrays.stream(request.getCookies()).filter(value ->
                        value.getName().equals("Authorization") || value.getName().equals("refresh")).toList();

        UserRegisterDTO userRegisterDTO = authService.getUserDetails(cookies);
        if (userRegisterDTO != null) {
            order.setClient(userRegisterDTO.getLogin());
        }

        Order finalOrder = save(order);
        AtomicReference<String> result = new AtomicReference<>();
        Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("basket")).findFirst().ifPresentOrElse(value -> {
            ListBasketItemDTO basket = basketService.getBasket(value);
            if (basket.getBasketProducts().isEmpty()) throw new RuntimeException();
            List<OrderItems> items = new ArrayList<>();
            basket.getBasketProducts().forEach(item -> {
                OrderItems orderItems = basketItemDTOToOrderItemsMapper.mapToOrderItems(item);
                orderItems.setOrder(finalOrder);
                orderItems.setUid(UUID.randomUUID().toString());
                items.add(itemService.save(orderItems));
                basketService.removeItemFromBasket(value, item.getUid());
            });
            result.set(payUService.createOrder(finalOrder, items));
            value.setMaxAge(0);
            response.addCookie(value);
            emailService.sendOrderMail(order.getEmail(), order.getUid());
        }, () -> {
            throw new RuntimeException();
        });
        return result.get();
    }

}

