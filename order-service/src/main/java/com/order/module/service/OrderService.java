package com.order.module.service;

import com.order.module.web.dto.OrderItemDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface OrderService {

    ResponseEntity<?> addPositionOrder(OrderItemDTO positionRequest, String token);

    ResponseEntity<?> executeOrder(String token);

    ResponseEntity<?> unСart(UUID idPosition, String token);

    ResponseEntity<?> getCart(String token);
}
