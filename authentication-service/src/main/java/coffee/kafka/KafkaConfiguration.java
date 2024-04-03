package coffee.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic newTopicOrder() {
        return new NewTopic(
                "user-id-order",
                1,
                (short) 1
        );

    }

    @Bean
    public NewTopic newTopicOrderRemove() {
        return new NewTopic(
                "user-id-order-remove",
                1,
                (short) 1
        );
    }

}
