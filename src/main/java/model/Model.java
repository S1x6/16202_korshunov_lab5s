package model;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private static int userId = 1;
    private static volatile Model instance;
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


}