package com.order.module.service;

import com.order.module.web.dto.OrderItemDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface OrderService {

    public ResponseEntity<?> addPositionOrder(OrderItemDTO positionRequest, String token);

    public ResponseEntity<?> executeOrder(String token);

    public ResponseEntity<?> unСart(UUID idPosition, String token);
}
