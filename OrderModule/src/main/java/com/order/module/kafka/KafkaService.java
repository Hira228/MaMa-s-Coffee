package com.order.module.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaService {

    private final ReplyingKafkaTemplate<String, Object, Object> template;

    @Value("${order.send-topics}")
    private String SEND_TOPICS_AUTH;

    @Value("${order.send-menu}")
    private String SEND_TOPICS_MENU;


    public Object authenticateUser(Object request) throws Exception {
        ProducerRecord<String, Object> record = new ProducerRecord<>(SEND_TOPICS_AUTH, request);
        RequestReplyFuture<String, Object, Object> replyFuture = template.sendAndReceive(record);
        SendResult<String, Object> sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
        ConsumerRecord<String, Object> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
        return consumerRecord.value();
    }
    public Object getMenu(Object request) throws Exception {
        ProducerRecord<String, Object> record = new ProducerRecord<>(SEND_TOPICS_MENU, request);
        RequestReplyFuture<String, Object, Object> replyFuture = template.sendAndReceive(record);
        SendResult<String, Object> sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
        ConsumerRecord<String, Object> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
        return consumerRecord.value();
    }

}