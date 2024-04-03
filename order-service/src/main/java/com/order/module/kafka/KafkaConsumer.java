package com.order.module.kafka;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class KafkaConsumer {

    RedisTemplate<String, Object> redisTemplate;

    @KafkaListener(topics = "user-id-order", groupId = "user-id-order-group")
    public void listenerId(ConsumerRecord<String, String> data) {
        if (data.key() != null && data.value() != null) {
            redisTemplate.opsForValue().set(data.key(), data.value());
        } else {
            log.debug("'user-id-order' came up with zero values.");
        }
    }

    @KafkaListener(topics = "user-id-order-remove", groupId = "user-id-order-group")
    public void listenerIdRemove(String key) {
        if (key != null) {
            redisTemplate.delete(key);
        } else {
            log.debug("'user-id-order-remove' came up with zero values.");
        }
    }
}
