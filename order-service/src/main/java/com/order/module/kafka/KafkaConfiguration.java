package com.order.module.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import java.time.Duration;

@Configuration
public class KafkaConfiguration {
    @Value("${ORDER-SERVICE.reply-topics}")
    private String REPLY_TOPICS;

    @Value("${ORDER-SERVICE.consumer-group}")
    private String CONSUMER_GROUPS;

    @Bean
    public ReplyingKafkaTemplate<String, Object, Object> replyingTemplate(
            ProducerFactory<String, Object> pf,
            ConcurrentMessageListenerContainer<String, Object> repliesContainer) {
        ReplyingKafkaTemplate<String, Object, Object> replyTemplate = new ReplyingKafkaTemplate<>(pf, repliesContainer);
        replyTemplate.setDefaultReplyTimeout(Duration.ofSeconds(10));
        replyTemplate.setSharedReplyTopic(true);
        return replyTemplate;
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, Object> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory) {
        ConcurrentMessageListenerContainer<String, Object> repliesContainer = containerFactory.createContainer(REPLY_TOPICS);
        repliesContainer.getContainerProperties().setGroupId(CONSUMER_GROUPS);
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }
}
