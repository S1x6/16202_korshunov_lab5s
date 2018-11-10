package model.json;

import model.Message;

import java.util.List;

public class MessageList {
    private List<Message> messages;

    public MessageList(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
