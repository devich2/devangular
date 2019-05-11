package com.dsr.dsr.model;

import java.util.Date;

public class Message {

    private String content;

    private Date activity;

    private User user;

    public Message(User user, String content) {
        this.user = user;
        this.content = content;
        updateActivity();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getActivity() {
        return activity;
    }

    public void setActivity(Date activity) {
        this.activity = activity;
    }

    public void updateActivity(){
        this.activity = new Date(System.currentTimeMillis());
    }
}
