package com.example.order.mediator;

import com.example.order.entity.OrderDTO;
import com.example.order.entity.Order;
import com.example.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMediator {

    private final com.example.order.translators.OrderDTOToOrderMapper orderDTOToOrderMapper;
    private final OrderService orderService;

    public ResponseEntity<?> createOrder(OrderDTO orderDTO, HttpServletRequest request, HttpServletResponse response) {
        Order order = orderDTOToOrderMapper.mapToOrder(orderDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE,"application/json");
        return ResponseEntity.status(302).headers(httpHeaders).body(orderService.createOrder(order, request, response));
    }
}

