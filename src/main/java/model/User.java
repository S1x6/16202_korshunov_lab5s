package model;

import model.json.UserObject;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    private static final int ONLINE_TIMEOUT = 5 * 1000;
    private static final int KICK_TIMEOUT = 15 * 1000;

    private int id;

    public String getName() {
        return name;
    }

    private String name;
    private long lastTimeActive;

    public String getToken() {
        return token;
    }

    private String token;

    public User(String name) {
        this.name = name;
        lastTimeActive = System.currentTimeMillis();
        this.token = generateMd5Hash(this.id + "_" + String.valueOf(lastTimeActive));
    }

    private static String generateMd5Hash(String toHash) {
        MessageDigest md;
        String myHash = "";
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(toHash.getBytes());
            byte[] digest = md.digest();
            myHash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return myHash;
    }

    public void updateTimeActive() {
        lastTimeActive = System.currentTimeMillis();
    }

    public boolean isOnline() {
        return System.currentTimeMillis() - lastTimeActive < ONLINE_TIMEOUT;
    }

    @Override
    public boolean equals(Object obj) {
        boolean withString = obj instanceof String && obj.equals(this.name);
        boolean withUser = obj instanceof User && super.equals(obj);
        return withString || withUser;
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public UserObject getUserObject() {
        return new UserObject(this.name, this.id, isOnline(), this.token);
    }

    public boolean shouldBeKicked() {
        return System.currentTimeMillis() - lastTimeActive >= KICK_TIMEOUT;
    }
}
