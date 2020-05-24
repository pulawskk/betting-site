package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.enums.BetSlipType;
import com.pulawskk.bettingsite.models.BetSlipSentDto;
import com.pulawskk.bettingsite.models.Selection;
import com.pulawskk.bettingsite.services.BetSlipService;
import com.pulawskk.bettingsite.services.UserService;
import com.pulawskk.bettingsite.services.WalletService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/settler")
@SessionAttributes("selections")
public class BetPlacementController {

    private final BetSlipService betSlipService;
    private final UserService userService;
    private final WalletService walletService;

    public BetPlacementController(BetSlipService betSlipService, UserService userService, WalletService walletService) {
        this.betSlipService = betSlipService;
        this.userService = userService;
        this.walletService = walletService;
    }

    @PostMapping(value = "/post", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String placeBet(@RequestBody BetSlipSentDto betSlipSentDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
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
        session.setAttribute("balance", walletService.findBalanceForUser(currentUser.getId()));
        System.out.println("please redirect");
        return "mainPageDecorated";
    }
}
