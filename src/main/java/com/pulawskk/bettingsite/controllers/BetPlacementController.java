package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.enums.BetSlipType;
import com.pulawskk.bettingsite.models.BetSlipSentDto;
import com.pulawskk.bettingsite.models.Selection;
import com.pulawskk.bettingsite.services.BetSlipService;
import com.pulawskk.bettingsite.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/post", produces= MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String afterPage(@RequestBody BetSlipSentDto betSlipSentDto) {

        List<Selection> selections = new ArrayList<>();
        betSlipSentDto.getSelections().forEach(selectionDto ->
            selections.add(Selection.builder()
                            .uniqueId(selectionDto.getUniqueId())
                            .competition(selectionDto.getCompetition())
                            .marketName(selectionDto.getEventName())
                            .marketType(selectionDto.getMarketType())
                            .value(selectionDto.getOdd())
                            .userType(selectionDto.getUserType()).build()
        ));

        List<BetSlipType> betSlipTypeList = betSlipSentDto.getTypes();
        String stakeRequest = betSlipSentDto.getStake().trim();
        User currentUser = userService.userLoggedIn();
        betSlipService.saveBetSlip(selections, stakeRequest, betSlipTypeList, currentUser);
        return "redirect:/events/football";
    }
}
