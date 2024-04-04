package com.order.module.service;

import com.order.module.entity.Order;
import com.order.module.entity.OrderItem;
import com.order.module.exceptions.AuthError;
import com.order.module.repository.OrderRepository;
import com.order.module.state.OrderStatus;
import com.order.module.web.dto.OrderItemDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService{
    OrderRepository orderRepository;
    RedisTemplate<String, Object> redisTemplate;


    @Override
    public ResponseEntity<?> addPositionOrder(OrderItemDTO positionRequest, String token) {
        String userIdStr = Objects.requireNonNull(redisTemplate.opsForValue().get(token.substring(7))).toString();
        if(userIdStr == null) return new ResponseEntity<>(new AuthError(HttpStatus.UNAUTHORIZED.value(), "Re-authorize"),  HttpStatus.UNAUTHORIZED);

        Order order = orderRepository.findByUserId(UUID.fromString(userIdStr));

        if(order == null) {
            order = new Order();

            order.setUserId(UUID.fromString(userIdStr));
            order.setPlacedAt(new Date());
            order.setStatus(OrderStatus.CREATED);

            orderRepository.save(order);
        }

        OrderItem orderItem = new OrderItem();

        orderItem.setMenuItemId(positionRequest.getMenuItemId());
        orderItem.setQuantity(positionRequest.getQuantity());
        orderItem.setPrice(positionRequest.getPrice().multiply(BigDecimal.valueOf(positionRequest.getQuantity())));
        if(order.getItems() == null) order.setItems(new ArrayList<>());
        order.getItems().add(orderItem);

        orderRepository.save(order);

        return new ResponseEntity<>(new AuthError(HttpStatus.OK.value(), "Position added successfully"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> executeOrder(String token) {
        String userIdStr = Objects.requireNonNull(redisTemplate.opsForValue().get(token.substring(7))).toString();
        if(userIdStr == null) return new ResponseEntity<>(new AuthError(HttpStatus.UNAUTHORIZED.value(), "Re-authorize"),  HttpStatus.UNAUTHORIZED);

        Order order = orderRepository.findByUserId(UUID.fromString(userIdStr));

        if(order == null) return new ResponseEntity<>(new AuthError(HttpStatus.NO_CONTENT.value(), "You have an empty shopping cart"), HttpStatus.NO_CONTENT);

        order.setStatus(OrderStatus.PROCESSING);

        orderRepository.save(order);

        // добавить логику удаления из бд и отправку по кафке ордер в сервис кухни!!!!

        return new ResponseEntity<>(new AuthError(HttpStatus.ACCEPTED.value(), "Your order has arrived in the kitchen. It will be ready soon."), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<?> unСart(UUID idPosition, String token) {
        String userIdStr = Objects.requireNonNull(redisTemplate.opsForValue().get(token.substring(7))).toString();
        if(userIdStr == null) return new ResponseEntity<>(new AuthError(HttpStatus.UNAUTHORIZED.value(), "Re-authorize"),  HttpStatus.UNAUTHORIZED);

        Order order = orderRepository.findByUserId(UUID.fromString(userIdStr));

        if(order == null) return new ResponseEntity<>(new AuthError(HttpStatus.NO_CONTENT.value(), "You have an empty shopping cart"), HttpStatus.NO_CONTENT);

        for (Iterator<OrderItem> iterator = order.getItems().iterator(); iterator.hasNext();) {
            OrderItem item = iterator.next();
            if (item.getMenuItemId().equals(idPosition)) {
                iterator.remove();
                orderRepository.save(order);
                return new ResponseEntity<>(new AuthError(HttpStatus.OK.value(), "Position removed successfully"), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new AuthError(HttpStatus.NOT_FOUND.value(), "No position with this id was found"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> getCart(String token) {
        String userIdStr = Objects.requireNonNull(redisTemplate.opsForValue().get(token.substring(7))).toString();
        if(userIdStr == null) return new ResponseEntity<>(new AuthError(HttpStatus.UNAUTHORIZED.value(), "Re-authorize"),  HttpStatus.UNAUTHORIZED);

        Order order = orderRepository.findByUserId(UUID.fromString(userIdStr));

        if(order == null) return new ResponseEntity<>(new AuthError(HttpStatus.NO_CONTENT.value(), "You have an empty shopping cart"), HttpStatus.NO_CONTENT);

        return ResponseEntity.ok().body(order);
    }
}
