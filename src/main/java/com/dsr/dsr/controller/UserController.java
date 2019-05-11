package com.dsr.dsr.controller;

import com.dsr.dsr.model.User;
import com.dsr.dsr.model.json.Response;
import com.dsr.dsr.model.json.Result;
import com.dsr.dsr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

//    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/api/chat/{name}/joinUser", method = RequestMethod.GET)
    @ResponseBody
    public Response joinUser(@CookieValue(value = "Token", required = false) String token, @PathVariable String name, HttpServletResponse response) {
        try {
            User user = userService.addUser(name, token);
            Cookie cookie = new Cookie("Token", user.getToken());
            System.out.println("RETARD : "+ name);
            cookie.setPath("/api/chat/"+name);
            response.addCookie(cookie);
            System.out.println("gg");
            return new Response(true, new Result("user", user.getToken(), user.getActivity()));
        } catch (Exception e) {
            return new Response(false, new Result(e.getMessage()));
        }
    }
    @RequestMapping(value = "/api/chat/{name}/disconnect", method = RequestMethod.GET)
    @ResponseBody
    public Response disconnectUser(@CookieValue(value = "Token", required = false) String token, @PathVariable String name) {
        try {
            System.out.println("AGA");
           userService.deleteUser(name,token);
           return new Response(true,new Result("Disconnected from chat"));

        } catch (Exception e) {
            return new Response(false, new Result(e.getMessage()));
        }
    }
}
