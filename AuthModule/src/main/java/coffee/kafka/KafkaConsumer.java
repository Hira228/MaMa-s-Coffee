package coffee.kafka;

import coffee.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final JwtTokenUtils jwtTokenUtils;

    @KafkaListener(groupId = "${auth.consumer-group}", topics = "${auth.send-topics}")
    @SendTo
    public Message<?> listen(ConsumerRecord<String, Object> consumerRecord) {
        return MessageBuilder.withPayload(Boolean.toString(jwtTokenUtils.isValidToken(consumerRecord.value().toString()))).build();
    }
}