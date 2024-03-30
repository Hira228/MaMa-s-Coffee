package com.order.module.web.controller;

import com.order.module.entity.MenuItem;
import com.order.module.service.MenuItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuItemsService menuItemsService;

    @GetMapping("/get-menu")
    public ResponseEntity<List<MenuItem>> getMenu() {
        return ResponseEntity.ok().body(menuItemsService.getAllPosition());
    }

}
