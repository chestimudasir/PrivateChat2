package com.cmk.privatechat2;

public class User {
    private String email;
    private String uid;

    public User(String email, String uid) {
        this.email = email;
        this.uid = uid;
    }

    public User(String email) {
        this.email = email;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
