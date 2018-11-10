package model;

public class Message {

    private String text;
    private int id;
    private int author;

    public Message(String text, int author) {
        this.text = text;
        this.id = id;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public int getAuthorId() {
        return author;
    }
}
