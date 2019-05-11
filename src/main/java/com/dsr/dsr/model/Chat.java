package com.dsr.dsr.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chat {
    private String name;

    private int maxUserCount;

    private int maxMessageCount;

    private int maxSymbolCount;

    private int interval;

    private Date activity;

    private List<Message> newMessages = new ArrayList<Message>();

    private List<Message> messages = new ArrayList<Message>();

    private List<User> users = new ArrayList<User>();

    public Chat(String name, int maxUserCount, int maxMessageCount, int maxSymbolCount, int interval) {
        this.name = name;
        this.maxUserCount = maxUserCount;
        this.maxMessageCount = maxMessageCount;
        this.maxSymbolCount = maxSymbolCount;
        this.interval = interval;
        updateActivity();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxUserCount() {
        return maxUserCount;
    }

    public void setMaxUserCount(int maxUserCount) {
        this.maxUserCount = maxUserCount;
    }

    public int getMaxMessageCount() {
        return maxMessageCount;
    }

    public void setMaxMessageCount(int maxMessageCount) {
        this.maxMessageCount = maxMessageCount > 0 ? maxMessageCount : 1;
    }

    public int getMaxSymbolCount() {
        return maxSymbolCount;
    }

    public void setMaxSymbolCount(int maxSymbolCount) {
        this.maxSymbolCount = maxSymbolCount;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    private void addMessage(Message mess) {
        this.messages.add(mess);
        deleteSpareMessages();
    }

    private void deleteSpareMessages() {
        int size = messages.size();
        for (int i = maxMessageCount - 1; i < size; i++) {
            this.messages.remove(0);
        }
    }

    private boolean checkMessageMaxSymbol(Message mess) throws Exception {
        if (mess.getContent().length() > maxSymbolCount){
            throw (new Exception("Symbol limit in message - " + maxSymbolCount));
        }
        else {
            return true;
        }
    }

    private boolean checkUserSpam(User user) throws Exception {
        if (System.currentTimeMillis() - user.getActivity().getTime() < interval * 1000) {
            throw (new Exception("Stop spam"));
        }
        else {
            return true;
        }
    }

    public void sendMessage(Message mess) throws Exception {
        User user = mess.getUser();
        if (checkUserSpam(user) && checkMessageMaxSymbol(mess)){
            addMessage(mess);
            newMessages.add(mess);
            user.updateActivity();
        }
        updateActivity();
    }

    public void addUser(User user){
        users.add(user);
        updateActivity();
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public User getUser(String token) {
        for (User user: users) {
            System.out.println("His token : "+ token);
            System.out.println("Tokens : " + user.getToken());
            if (user.getToken().equals(token)) {
                System.out.println("Authorized with token");
                return user;
            }
        }
        return null;
    }

    public void updateActivity(){
        this.activity = new Date(System.currentTimeMillis());
    }

    public Date getActivity() {
        return activity;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Message> getAll() {
        return messages;
    }

    public List<Message> getAllNewMessages() {
        List<Message> allNew = new ArrayList<>(newMessages);
        newMessages.clear();
        return allNew;
    }
}
