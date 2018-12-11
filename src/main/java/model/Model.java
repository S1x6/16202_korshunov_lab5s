package model;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private static int userId = 1;
    private static int messageId = 1;
    private static volatile Model instance;
    private static OnServerMessageAddedListener sMessageListener = null;
    private List<User> users;
    private List<Message> messages;

    private Model() {
        users = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public static Model getInstance() {
        if (instance == null) {
            synchronized (Model.class) {
                if (instance == null) {
                    instance = new Model();
                }
            }
        }
        return instance;
    }

    public static void setsMessageListener(OnServerMessageAddedListener sMessageListener) {
        Model.sMessageListener = sMessageListener;
    }

    public List<User> getUsers() {
        return users;
    }

    public User addUser(String name) {
        User user = new User(name);
        user.setId(userId);
        userId++;
        users.add(user);
        return user;
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public Message addMessage(String text, int authorId, String authorName) {
        Message message = new Message(text, authorId, authorName);
        message.setId(messageId);
        messageId++;
        messages.add(message);
        return message;
    }

    public Message addServerMessage(String text) {
        Message message = new Message(text, 0, "Server");
        message.setId(messageId);
        messageId++;
        messages.add(message);
        if (sMessageListener != null) {
            sMessageListener.onAdd(message);
        }
        return message;
    }

    public List<Message> getMessages(int offset, int count) {
        if (offset >= messages.size()) {
            return new ArrayList<>();
        }
        return messages.subList(offset, offset+count < messages.size() ? offset+count : messages.size());
    }

    public interface OnServerMessageAddedListener {
        void onAdd(Message msg);
    }
}