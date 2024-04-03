package com.order.module.web.controller;

import com.order.module.service.OrderService;
import com.order.module.web.dto.OrderItemDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/add-position")
    public ResponseEntity<?> addItemToCart(@RequestBody OrderItemDTO positionRequest,
                                           @RequestHeader("Authorization") String authorizationHeader) {

        return orderService.addPositionOrder(positionRequest, authorizationHeader);
    }

    @PostMapping("/execute-order")
    public ResponseEntity<?> executeOrder(@RequestHeader("Authorization") String authorizationHeader) {
        return orderService.executeOrder(authorizationHeader);
    }

    @DeleteMapping("/delete-position/{idPosition}")
    public ResponseEntity<?> unСart(@PathVariable UUID idPosition,
                                    @RequestHeader("Authorization") String authorizationHeader) {
        return orderService.unСart(idPosition, authorizationHeader);
    }
}
