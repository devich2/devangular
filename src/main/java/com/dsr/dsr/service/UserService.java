package com.dsr.dsr.service;

import com.dsr.dsr.model.Chat;
import com.dsr.dsr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private long maxTimeAfk = 3600;

    @Autowired
    private ChatService chatService;

    private User getUserFromChat(Chat chat, String token) throws Exception {
        User user = chat.getUser(token);
        if (user == null) {
            System.out.println("bad");
            throw (new Exception("User not found"));
        }
        else {
            return user;
        }
    }

    public User getUser(String name, String token) throws Exception {
        return getUserFromChat(chatService.getChat(name), token);
    }

    public User getUser(Chat chat, String token) throws Exception {
        return getUserFromChat(chat, token);
    }

    private User addUserToChat(Chat chat, String token) throws Exception {
        User user = chat.getUser(token);
        if (user == null) {
            //System.out.println("Creating token");
            User new_user = new User(token);
            if(!availableFreeSlots(chat)) {
                deleteInactiveUsers(chat);
                if (!availableFreeSlots(chat)) {
                    throw new Exception("Chat is full!");
                }
            }
            chat.addUser(new_user);
            return new_user;
        }
        else {
            return null;
        }
    }

    public boolean availableFreeSlots(Chat chat) {
        return chat.getUsers().size() < chat.getMaxUserCount();
    }

    public void deleteInactiveUsers(Chat chat){
        List<User> userList = chat.getUsers();
        Date lastTime = new Date(System.currentTimeMillis() - maxTimeAfk * 1000);
        for (User user : userList)
        {
         if (lastTime.before(user.getActivity()))
          {
            userList.remove(user);
          }
        }
    }

    private User createUser(Chat chat) throws Exception {
        User user = addUserToChat(chat, UUID.randomUUID().toString());
        while (user == null) {
            user = addUserToChat(chat, UUID.randomUUID().toString());
        }
        return user;
    }
    public void deleteUser(String name,String token)throws Exception {
        Chat chat = chatService.getChat(name);
        User user = getUser(chat,token);
        List<User> userList = chat.getUsers();
        userList.remove(user);
        System.out.println("Deleted user");

    }
    public User addUser(String name, String token) throws Exception {
        Chat chat = chatService.getChat(name);
        if (token == null) {
            return createUser(chat);
        }
        else {

            User user = addUserToChat(chat, token);
            if (user == null) {
                throw (new Exception("You are already in this chat"));
            }
            else {
                return user;
            }
        }
    }
}
