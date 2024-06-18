package ru.abolsoft.core.service.kafka;

import org.springframework.stereotype.Service;
import ru.abolsoft.common.kafka.dto.MessageToSend;
import ru.abolsoft.core.entity.Account;

import java.util.Map;

@Service
public class MessageFactory {

    public MessageToSend welcomeMessage(Account account) {

        String title = "Успешная регистрация";

        String text = String.format("""
                %s

                Привет %s, теперь ты можешь использовать \
                облако для хранения изображений или фото!""", title, account.getName());

        MessageToSend messageToSend = new MessageToSend();
        messageToSend.setEmail(account.getEmail());
        messageToSend.setTitle(title);
        messageToSend.setText(text);

        return messageToSend;
    }

    public MessageToSend uploadMessage(Account account, Map<String, Long> fileData) {

        String title = "Файлы загружены в облако";

        StringBuilder stringBuilder = new StringBuilder();
        long sumBytes = 0L;

        for (Map.Entry<String, Long> file : fileData.entrySet()) {
            sumBytes += file.getValue();
            stringBuilder
                    .append(file.getKey())
                    .append(" Размер файла: %d байт".formatted(file.getValue()))
                    .append(System.lineSeparator());
        }

        String text = String.format("%s\n\nВсего загружено %d байт.\n\nСписок файлов:\n%s",
                title, sumBytes, stringBuilder);

        MessageToSend messageToSend = new MessageToSend();
        messageToSend.setEmail(account.getEmail());
        messageToSend.setTitle(title);
        messageToSend.setText(text);

        return messageToSend;
    }

    public MessageToSend downloadMessage(Account account, String fileName, long sizeImage) {

        String title = "Успешная загрузка файла";

        String text = String.format("%s\n\nВы загрузили файл %s из облака. Размер файла: %d",
                title, fileName, sizeImage);

        MessageToSend messageToSend = new MessageToSend();
        messageToSend.setEmail(account.getEmail());
        messageToSend.setTitle(title);
        messageToSend.setText(text);

        return messageToSend;
    }
}
