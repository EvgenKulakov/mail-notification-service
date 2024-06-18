package ru.abolsoft.core.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.abolsoft.common.kafka.dto.MessageToSend;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, MessageToSend> kafkaTemplate;


    public String publicMessage(MessageToSend messageToSend) {
        String messageId = UUID.randomUUID().toString();
        messageToSend.setMessageId(messageId);

        CompletableFuture<SendResult<String, MessageToSend>> future = kafkaTemplate
                .send(MessageToSend.TOPIC_NAME, messageId, messageToSend);

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error(exception.getMessage());
            } else {
                log.info("Message send successfully {}", result.getRecordMetadata());
            }
        });

        return messageId;
    }
}