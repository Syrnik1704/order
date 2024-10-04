package com.example.order.service;

import com.example.order.entity.*;
import com.example.order.mappers.BasketItemDTOToOrderItemsMapper;
import com.example.order.repository.DeliverRepository;
import com.example.order.repository.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeliverRepository deliverRepository;
    private final BasketService basketService;
    private final ItemService itemService;
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

    public void createOrder(Order order, HttpServletRequest request) {
        Order finalOrder = save(order);
        Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("basket")).findFirst().ifPresentOrElse(value -> {
            ListBasketItemDTO basket = basketService.getBasket(value);
            List<OrderItems> items = new ArrayList<>();
            basket.getBasketProducts().forEach(item -> {
                OrderItems orderItems = basketItemDTOToOrderItemsMapper.mapToOrderItems(item);
                orderItems.setOrder(finalOrder);
                orderItems.setUid(UUID.randomUUID().toString());
                items.add(itemService.save(orderItems));
            });
        }, () -> {
            throw new RuntimeException();
        });
    }

}

