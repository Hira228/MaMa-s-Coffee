package com.order.module.initializr;

import com.order.module.entity.MenuItem;
import com.order.module.repository.MenuItemsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MenuDataInitializer implements CommandLineRunner {

    MenuItemsRepository menuItemsRepository;

    @Override
    public void run(String... args) throws Exception {
        MenuItem menuItem1 = new MenuItem("Coffee", "Delicious coffee", BigDecimal.valueOf(2.5));
        MenuItem menuItem2 = new MenuItem("Latte", "Latte with frothy milk", BigDecimal.valueOf(3.0));
        MenuItem menuItem3 = new MenuItem("Cappuccino", "Classic cappuccino", BigDecimal.valueOf(3.5));
        MenuItem menuItem4 = new MenuItem("Macchiato", "Espresso with a dash of milk", BigDecimal.valueOf(3.0));
        MenuItem menuItem5 = new MenuItem("Raf", "Espresso with steamed milk and vanilla syrup", BigDecimal.valueOf(4.0));

        menuItemsRepository.save(menuItem1);
        menuItemsRepository.save(menuItem2);
        menuItemsRepository.save(menuItem3);
        menuItemsRepository.save(menuItem4);
        menuItemsRepository.save(menuItem5);

        System.out.println("Menu items initialized successfully.");
    }
}
