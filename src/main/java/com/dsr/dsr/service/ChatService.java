package com.dsr.dsr.service;

import com.dsr.dsr.model.Chat;
import com.dsr.dsr.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private Storage storage;

    public Chat getChat(String name) throws Exception {
        Chat chat = storage.getChat(name);
        if (chat == null) {
            System.out.println("CHAT BAD");
            throw (new Exception("This chat is not created"));
        }
        else return chat;
    }

    public Chat addChat(String name, int maxUserCount, int maxMessageCount, int maxSymbolCount, int interval) throws Exception {
        Chat chat = new Chat(name, maxUserCount, maxMessageCount, maxSymbolCount, interval);
        storage.addChat(chat);
        return chat;
    }
}
