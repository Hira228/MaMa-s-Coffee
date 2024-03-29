package com.order.module.web.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.module.exceptions.AuthError;
import com.order.module.kafka.KafkaService;
import com.order.module.web.dto.MenuItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class OrderController {
    private final KafkaService kafkaService;

    @GetMapping("/order/getMenu")
    public ResponseEntity<?> sendToken(@RequestHeader("Authorization") String token) throws Exception {
        boolean res = Boolean.parseBoolean(kafkaService.authenticateUser(token).toString());
        if (!res) return new ResponseEntity<>(new AuthError(HttpStatus.UNAUTHORIZED.value(),
                "Invalid authentication token"), HttpStatus.UNAUTHORIZED);

        Object menuObject = kafkaService.getMenu("menu");
        ObjectMapper objectMapper = new ObjectMapper();
        List<MenuItemDTO> menuItems = objectMapper.readValue((String) menuObject, new TypeReference<List<MenuItemDTO>>() {});


        List<MenuItemDTO> menuItemDTOs = menuItems.stream()
                .map(menuItem -> new MenuItemDTO(menuItem.getId(), menuItem.getName(), menuItem.getDescription(), menuItem.getPrice()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(menuItemDTOs, HttpStatus.OK);

    }

}
