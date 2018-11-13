package model;

public class Message {

    private String text;
    private int id;
    private int author;
    private String authorName;

    public Message(String text, int author, String authorName) {
        this.text = text;
        this.authorName = authorName;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
