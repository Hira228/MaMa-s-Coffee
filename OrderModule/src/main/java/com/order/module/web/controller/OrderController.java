package com.order.module.web.controller;

import com.order.module.exceptions.AuthError;
import com.order.module.kafka.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class OrderController {
    private final KafkaService kafkaService;

    @PostMapping("/kafka/send")
    public ResponseEntity<?> sendToken(@RequestHeader("Authorization") String token) throws Exception {
        boolean res = Boolean.parseBoolean(kafkaService.kafkaRequestReply(token).toString());
        if (!res) {
            return new ResponseEntity<>(new AuthError(HttpStatus.UNAUTHORIZED.value(), "Invalid authentication token"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new AuthError(HttpStatus.OK.value(), "Valid authentication token"), HttpStatus.OK);
    }

}
