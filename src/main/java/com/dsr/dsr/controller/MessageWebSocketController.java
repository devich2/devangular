package com.dsr.dsr.controller;


import com.dsr.dsr.model.Message;
import com.dsr.dsr.model.json.Response;
import com.dsr.dsr.model.json.Result;
import com.dsr.dsr.model.websocket.SMessage;
import com.dsr.dsr.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageWebSocketController {

    @Autowired
    private MessageService messageService;


    @MessageMapping("/socket/chat/{name}/sendmessage")
    @SendTo("/topic/{name}")
    public Response SendToSocket(SMessage smess, @DestinationVariable(value = "name") String name) throws Exception {
        String token = smess.getToken();
        System.out.println("mess :" + smess.getContent());
        System.out.println("name :" + name);
        System.out.println("token :" + token);
      try {
          //messageService.sendMessage(name, token, smess.getContent());
         List<Message> newMessages = messageService.getAllNewMessages(name, token);
         List<Result> results = new ArrayList<Result>();
         //results.add(new Result("mes",smess.getContent()));
         for (Message mess: newMessages) {
            results.add(new Result("message", mess.getContent(), mess.getActivity()));
          }
            return new Response(true, results);
       }
       catch (Exception e) {
     return new Response(false, new Result(e.getMessage()));
       }
    }
}
