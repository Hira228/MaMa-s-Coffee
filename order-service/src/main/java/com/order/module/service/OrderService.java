package com.order.module.service;

import com.order.module.web.dto.OrderItemDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    public ResponseEntity<?> addPositionOrder(OrderItemDTO positionRequest, String token);

    public ResponseEntity<?> executeOrder(String token);
}
