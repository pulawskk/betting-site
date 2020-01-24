package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/", "/index"})
    public String indexPage() {

        return "homeView";
    }

    @GetMapping(value = "/after")
    public String afterPage() {
//        if(userService.userLoggedIn() == null) {
//            return "login";
//        }
        System.out.println("after -> " + userService.displayAuthName());
        return "after";
    }

    @GetMapping(value = {"/home"})
    public String homePage(HttpSession session) {
        if(userService.userLoggedIn() != null) {
            session.setAttribute("isLoggedIn", true);
        }
        System.out.println("home -> " + userService.displayAuthName());
        return "indexMain";
    }

    @GetMapping(value = "/login")
    public String loginCheck() {
        System.out.println("login -> " + userService.displayAuthName());
        return "login";
    }
}
