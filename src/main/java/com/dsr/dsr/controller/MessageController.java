package com.dsr.dsr.controller;

import com.dsr.dsr.model.Message;
import com.dsr.dsr.model.json.Response;
import com.dsr.dsr.model.json.Result;
import com.dsr.dsr.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

//    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
    @RequestMapping(value = "/api/chat/{name}/sendmessage", method = RequestMethod.GET)
    @ResponseBody
    public Response sendMessage(@CookieValue(value = "Token", required = true) String token ,@PathVariable String name, @RequestParam(value = "content") String content,HttpServletResponse res) {
        try {
            System.out.println("TOken to write : " + token);
            System.out.println(name + "content : " + content);

            Message mess = messageService.sendMessage(name, token, content);
            return new Response(true, new Result("message", mess.getContent(), mess.getActivity()));
        } catch (Exception e) {
            return new Response(false, new Result(e.getMessage()));
        }
    }

//    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/chat/{name}/getAll", method = RequestMethod.GET)
    @ResponseBody
    public Response getAll(@PathVariable String name, @CookieValue(value = "Token", required = false) String token) {
        try {
            List<Result> results = new ArrayList<Result>();
            for (Message mess : messageService.getAll(name, token)) {
                results.add(new Result("message", mess.getContent(), mess.getActivity()));
            }
            Collections.reverse(results);
            return new Response(true, results);
        } catch (Exception e) {
            return new Response(false, new Result(e.getMessage()));
        }
    }
}
