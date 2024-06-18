package ru.abolsoft.mail.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.abolsoft.common.kafka.dto.MessageToSend;
import ru.abolsoft.mail.send.service.EmailSendService;

@Slf4j
@Component
@KafkaListener(topics = MessageToSend.TOPIC_NAME)
public class KafkaConsumer {

    @Autowired
    private EmailSendService emailSendService;

    @KafkaHandler
    public void handleMessage(MessageToSend messageToSend) {
        log.info("Reseved event {}", messageToSend.getMessageId() + messageToSend.getTitle());
        emailSendService.sendSimpleMessage(messageToSend);
    }
}
