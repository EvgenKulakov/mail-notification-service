package ru.abolsoft.core.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import ru.abolsoft.common.kafka.dto.MessageToSend;

@Configuration
public class KafkaConfig {

    @Bean
    NewTopic createTopic() {
        return TopicBuilder
                .name(MessageToSend.TOPIC_NAME)
                .partitions(3)
                .replicas(1)
                .build();
    }
}