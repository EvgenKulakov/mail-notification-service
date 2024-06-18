package ru.abolsoft.core.service.kafka;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.abolsoft.common.kafka.dto.MessageToSend;
import ru.abolsoft.core.entity.Account;

@Service
public class MessageFactory {

    public MessageToSend welcomeMessage(Account account) {

        String title = "Успешная регистрация";

        String text = String.format("Привет %s, теперь ты можешь использовать облако для хранения изображений или фото!",
                account.getName());

        MessageToSend messageToSend = new MessageToSend();
        messageToSend.setEmail(account.getEmail());
        messageToSend.setTitle(title);
        messageToSend.setText(text);

        return messageToSend;
    }

    public MessageToSend uploadMessage(Account account, MultipartFile[] files) {

        String title = "Файлы загружены в облако";

        StringBuilder stringBuilder = new StringBuilder();
        long sumBytes = 0L;

        for (MultipartFile file: files) {
            sumBytes += file.getSize();
            stringBuilder.append(file.getOriginalFilename()).append(System.lineSeparator());
        }

        String text = String.format("Всего загружено %d байт.\n\nСписок файлов:\n%s", sumBytes, stringBuilder);

        MessageToSend messageToSend = new MessageToSend();
        messageToSend.setEmail(account.getEmail());
        messageToSend.setTitle(title);
        messageToSend.setText(text);

        return messageToSend;
    }

    public MessageToSend downloadMessage(Account account, String fileName, long sizeImage) {

        String title = "Успешная загрузка файла";

        String text = String.format("Вы загрузили файл %s из облака. Размер файла: %d", fileName, sizeImage);

        MessageToSend messageToSend = new MessageToSend();
        messageToSend.setEmail(account.getEmail());
        messageToSend.setTitle(title);
        messageToSend.setText(text);

        return messageToSend;
    }
}
