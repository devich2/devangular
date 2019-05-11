package com.dsr.dsr.service;

import com.dsr.dsr.model.Chat;
import com.dsr.dsr.model.Message;
import com.dsr.dsr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    public Message sendMessage(String name, String token, String content) throws Exception {
        Chat chat = chatService.getChat(name);
        User user = userService.getUser(chat, token);
        Message message = new Message(user, content);
        chat.sendMessage(message);
        return message;
    }

    public List<Message> getAll(String name, String token) throws Exception {
        Chat chat = chatService.getChat(name);
        User user = userService.getUser(chat, token);
        return chat.getAll();
    }

    public List<Message> getAllNewMessages(String name, String token) throws Exception {
        Chat chat = chatService.getChat(name);
        User user = userService.getUser(chat, token);
        return chat.getAllNewMessages();
    }

}
