package com.example.order.service;

import com.example.order.entity.Deliver;
import com.example.order.entity.Order;
import com.example.order.entity.Status;
import com.example.order.repository.DeliverRepository;
import com.example.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeliverRepository deliverRepository;


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

    public void createOrder(Order order) {
        order = save(order);
    }

}

