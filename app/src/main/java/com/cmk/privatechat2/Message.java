package com.cmk.privatechat2;

public class Message {
    private String email;
    private String message;

    public Message(String email, String message) {
        this.email = email;
        this.message = message;
    }

    public Message() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
