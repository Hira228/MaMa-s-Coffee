package com.order.module.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.module.entity.MenuItem;
import com.order.module.service.MenuItemsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final MenuItemsService menuItemsService;
    private final ObjectMapper objectMapper;

    @KafkaListener(groupId = "${menu.consumer-group-menu}", topics = "${menu.send-menu}")
    @SendTo
    public Message<?> listen(ConsumerRecord<String, Object> consumerRecord) throws JsonProcessingException {
        List<MenuItem> menuItems = menuItemsService.getAllPosition();
        return MessageBuilder.withPayload(objectMapper.writeValueAsString(menuItems)).build();
    }
}