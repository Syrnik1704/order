package com.example.order.mediator;

import com.example.order.entity.OrderDTO;
import com.example.order.entity.Order;
import com.example.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMediator {

    private final com.example.order.translators.OrderDTOToOrderMapper orderDTOToOrderMapper;
    private final OrderService orderService;

    public ResponseEntity<?> createOrder(OrderDTO orderDTO, HttpServletRequest request) {
        Order order = orderDTOToOrderMapper.mapToOrder(orderDTO);
        orderService.createOrder(order, request);
        return null;
    }
}

