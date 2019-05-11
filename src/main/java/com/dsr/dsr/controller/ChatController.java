package com.dsr.dsr.controller;

import com.dsr.dsr.model.Chat;
import com.dsr.dsr.model.json.Response;
import com.dsr.dsr.model.json.Result;
import com.dsr.dsr.service.ChatService;
import com.dsr.dsr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;
//    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/createChat", method = RequestMethod.GET)
    @ResponseBody
    public Response sendMessage(@RequestParam(value = "name") String name,
                                @RequestParam(value = "maxUserCount") int maxUserCount,
                                @RequestParam(value = "maxMessageCount") int maxMessageCount,
                                @RequestParam(value = "maxSymbolCount") int maxSymbolCount,
                                @RequestParam(value = "interval") int interval,
                                HttpServletResponse response) {
        try {
            Chat chat = chatService.addChat(name, maxUserCount, maxMessageCount, maxSymbolCount, interval);
            /////////
            //response.sendRedirect("/chat/"+name+"/"+"joinUser");
            /////////
            return new Response(true, new Result("chat", chat.getName(), chat.getActivity()));
        } catch (Exception e) {
            return new Response(false, new Result(e.getMessage()));
        }
    }

//    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/api/chat/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Response joinUser(@PathVariable String name, @CookieValue(value = "Token", required = false) String token) {
        try {

            Chat chat = chatService.getChat(name);
            if (token == null)
            {
                System.out.println("Need creating token");
                return new Response(false, new Result("1"));
            }
            else
            {
                userService.addUser(name,token);
            }
           return new Response(true, new Result(token,chat.getName()));
        } catch (Exception e) {
            return new Response(false, new Result(e.getMessage()));
        }
    }
}