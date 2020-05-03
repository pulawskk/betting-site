package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/", "/index"})
    public String indexPage() {

        return "homeView";
    }

    @GetMapping(value = {"/home"})
    public String homePage(HttpSession session) {
        if(userService.userLoggedIn() != null) {
            session.setAttribute("isLoggedIn", true);
        }
        System.out.println("home -> " + userService.displayAuthName());
        return "index";
    }

    @GetMapping(value = "/login")
    public String loginCheck() {
        System.out.println("login -> " + userService.displayAuthName());
        return "login";
    }

    @GetMapping(value = {"/logout"})
    public String logout(HttpSession session) {
        session.setAttribute("isLoggedIn", null);
        System.out.println("sucessfully logged out!");
        System.out.println("home -> " + userService.displayAuthName());
        return "homeView";
    }
}
