package com.dsr.dsr.storage;

import com.dsr.dsr.model.Chat;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class Storage {

    private List<Chat> chats = Collections.synchronizedList(new ArrayList<Chat>());

    private static long maxLifeSecs = 10000;

    public void addChat(Chat chat) throws Exception {
        Chat chat1 = getChat(chat.getName());
        if (chat1 != null){
            throw(new Exception("Chat is already exists"));
        }
        else {
            this.chats.add(chat);
            System.out.println("Created");
            //deleteSpareChats();
        }
    }

    public Chat getChat(String name) {
        for (Chat chat: chats) {
            System.out.println("Chats : "+chat.getName());
            System.out.println("Mychat : "+name);
            if (chat.getName().equals(name)) {
                return chat;
            }
        }
        return null;
    }

    private void deleteSpareChats() {
        Date last_date = new Date(System.currentTimeMillis() - maxLifeSecs * 1000);
        for (Chat chat: chats) {
            if (chat.getActivity().before(last_date)){
                chats.remove(chat);
            }
        }
    }
}
