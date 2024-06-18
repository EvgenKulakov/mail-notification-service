package ru.abolsoft.mail.send.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.abolsoft.common.kafka.dto.MessageToSend;

@Service
public class EmailSendService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMessage(MessageToSend messageToSend) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(messageToSend.getEmail());
        message.setSubject(messageToSend.getTitle());
        message.setText(messageToSend.getText());
        mailSender.send(message);
    }
}
