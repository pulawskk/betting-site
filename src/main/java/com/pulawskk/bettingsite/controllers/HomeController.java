package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.services.UserService;
import com.pulawskk.bettingsite.services.WalletService;
import com.pulawskk.bettingsite.services.impl.BetServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final UserService userService;
    private final WalletService walletService;

    public HomeController(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }

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
        session.setAttribute("balance", walletService.findBalanceForUser(loggedInUser.getId()));

        return "mainPageDecorated";
    }

    @GetMapping("/welcomeBoard")
    public String welcomeBoard(){
        return "welcomePageDecorated";
    }

    @GetMapping(value = "/login")
    public String loginCheck() {
        logger.info("[" + getClass().getSimpleName()
                + "] method: loginCheck, logged in user: " + userService.displayAuthName());
        return "login";
    }

    @GetMapping(value = {"/logout"})
    public String logout(HttpSession session) {
        session.setAttribute("isLoggedIn", null);
        logger.info("[" + getClass().getSimpleName()
                + "] method: logout, successfully logged out user: " + userService.displayAuthName());
        return "homeView";
    }
}
