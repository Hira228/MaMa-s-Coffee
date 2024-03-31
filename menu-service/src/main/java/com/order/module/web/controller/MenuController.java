package com.order.module.web.controller;

import com.order.module.entity.MenuItem;
import com.order.module.service.MenuItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuItemsService menuItemsService;

    @GetMapping("/get-menu")
    public ResponseEntity<List<MenuItem>> getMenu() {
        return ResponseEntity.ok().body(menuItemsService.getAllPosition());
    }

    @GetMapping("get-menu-item/{menuItemId}")
    public ResponseEntity<MenuItem> getItem(@PathVariable UUID menuItemId) {

        Optional<MenuItem> item = menuItemsService.getPosition(menuItemId);
        return item.map(menuItem -> ResponseEntity.ok().body(menuItem)).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
