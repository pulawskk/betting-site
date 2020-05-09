package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.entities.BetSlip;
import com.pulawskk.bettingsite.services.BetSlipService;
import com.pulawskk.bettingsite.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.*;

@Controller
@RequestMapping("/betSlips")
public class BetController {

    private final BetSlipService betSlipService;
    private final UserService userService;

    public BetController(BetSlipService betSlipService, UserService userService) {
        this.betSlipService = betSlipService;
        this.userService = userService;
    }

    @GetMapping("/list/{status}")
    public String displayBetSLips(@PathVariable String status, Model model) {
        List<BetSlip> betSlips = new ArrayList<>();

        Long userId = userService.userLoggedIn().getId();

        if (status.equals("active")) {
            betSlips = betSlipService.betSlipsActiveForUser(userId);
        }

        model.addAttribute("betSlips", betSlips);
        return "displayBetSlipsDecorated";
    }
}
