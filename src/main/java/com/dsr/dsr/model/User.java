package com.dsr.dsr.model;

import java.util.Date;

public class User {
    private String token;

    private Date activity;

    public User(String token) {
        this.token = token;
        updateActivity();
    }

    public Date getActivity() {
        return activity;
    }

    public void setActivity(Date activity) {
        this.activity = activity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void updateActivity(){
        this.activity = new Date(System.currentTimeMillis());
    }
}
