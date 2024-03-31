package com.order.module.web.controller;

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

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/order")
public class OrderController {
    private final RestTemplate restTemplate;

    /// нужно придумать что то получше
    /// c тем чтобы получать id пользователя
    /// потому что http синхронный и из-за этого приложение при больших нагрузках будет тормозить.....
    @PostMapping("/add-position")
    public ResponseEntity<?> addItemToCart(@RequestBody OrderItemDTO positionRequest,
                                           HttpServletRequest request) {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:8000/auth/get-id",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        String responseBody = responseEntity.getBody();


        return ResponseEntity.ok().body(responseBody);
    }

}
