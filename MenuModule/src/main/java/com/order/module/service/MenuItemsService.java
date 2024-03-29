package com.order.module.service;

import com.order.module.entity.MenuItem;
import com.order.module.repository.MenuItemsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MenuItemsService {
    MenuItemsRepository menuItemsRepository;

    public List<MenuItem> getAllPosition() {
        return menuItemsRepository.findAll();
    }
}
