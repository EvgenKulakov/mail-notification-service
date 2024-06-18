package ru.abolsoft.common.kafka.dto;

public class MessageToSend {

    public static final String TOPIC_NAME = "message-for-mail-topic";

    private String messageId;
    private String email;
    private String title;
    private String text;

    public MessageToSend() {
    }

    public MessageToSend(String messageId, String email, String title, String text) {
        this.messageId = messageId;
        this.email = email;
        this.title = title;
        this.text = text;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
