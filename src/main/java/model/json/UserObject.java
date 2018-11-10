package model.json;

public class UserObject {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String username;
    private int id;
    private boolean isOnline;
    private String token;

    public UserObject(String username, int id, boolean isOnline, String token) {
        this.username = username;
        this.id = id;
        this.isOnline = isOnline;
        this.token = token;
    }
}
