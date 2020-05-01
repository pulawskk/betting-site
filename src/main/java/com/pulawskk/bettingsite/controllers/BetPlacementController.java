package com.pulawskk.bettingsite.controllers;

import com.google.gson.JsonObject;
import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.models.BetSlipDto;
import com.pulawskk.bettingsite.models.BetSlipSentDto;
import com.pulawskk.bettingsite.models.Selection;
import com.pulawskk.bettingsite.services.BetSlipService;
import com.pulawskk.bettingsite.services.UserService;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/settler")
@SessionAttributes("selections")
public class BetPlacementController {

    private final BetSlipService betSlipService;
    private final UserService userService;

    public BetPlacementController(BetSlipService betSlipService, UserService userService) {
        this.betSlipService = betSlipService;
        this.userService = userService;
    }

    @PostMapping("/bet")
    public String displayOddValue(@ModelAttribute Selection s, Model model) {
        List<Selection> selections = (List<Selection>) model.getAttribute("selections");
        if(selections == null) {
            selections = new ArrayList<>();
        }
        selections.add(s);
        model.addAttribute("selections", selections);
        selections.forEach(System.out::println);
        System.out.println(selections.size());
        return "redirect:/events/football";
    }

    @PostMapping("/placeBet")
    public String placeBetSlip(@ModelAttribute BetSlipDto betSlip, HttpServletRequest request, Model model) {
        List<Selection> selections = (List<Selection>) model.getAttribute("selections");
        if(selections == null) {
            selections = new ArrayList<>();
        }

        System.out.println("STAKE: <--> " + betSlip.getStake());
        String single = request.getParameter("singleBetSlip");
        String multi = request.getParameter("multiBetSlip");
        System.out.println("bet slip type: " + single);
        System.out.println("bet slip type: " + multi);
        List<String> betSlipTypeList = new ArrayList<>();
        betSlipTypeList.add(single);
        betSlipTypeList.add(multi);

        String stakeRequest = request.getParameter("stake");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());

        betSlipService.saveBetSlip(selections, stakeRequest, betSlipTypeList, currentUser);
        if (model.containsAttribute("selections")) {
            selections.clear();
            model.addAttribute("selections", selections);
        }
        if (selections.size() == 0) {

        } else {
            System.out.println(">>> BETSLIP stake -> " + stakeRequest);
            selections.forEach(selection -> {
                System.out.print(selection.getUniqueId());
                System.out.print(" | ");
                System.out.print(selection.getMarketName());
                System.out.print(" | ");
                System.out.print(selection.getUserType());
                System.out.print("\n");
            });
        }
        return "redirect:/events/football";
    }

    @PostMapping(value = "/post")
    public String afterPage(@RequestBody BetSlipSentDto s) {
        System.out.println("after -> " + userService.displayAuthName());
        System.out.println("types: " + s.getType());
        return "redirect:/events/football";
    }
}
