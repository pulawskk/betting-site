package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Value("${ipnetwork.app.bs}")
    private String projectIp;

    @Value("${server.port}")
    private String projectPort;

    @GetMapping(value = {"/mainBoard"})
    public String homePage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User loggedInUser = userService.userLoggedIn();
        session.setAttribute("userName", loggedInUser.getName());
        session.setAttribute("isLoggedIn", true);
        session.setAttribute("appIp", projectIp);
        session.setAttribute("appPort", projectPort);

        return "mainPageDecorated";
    }

    @GetMapping("/welcomeBoard")
    public String welcomeBoard(){
        return "welcomePageDecorated";
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
