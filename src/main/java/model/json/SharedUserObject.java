package model.json;

import model.User;

public class SharedUserObject {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    private String username;
    private int id;
    private boolean isOnline;

    public SharedUserObject(String username, int id, boolean isOnline) {
        this.username = username;
        this.id = id;
        this.isOnline = isOnline;
    }
}
