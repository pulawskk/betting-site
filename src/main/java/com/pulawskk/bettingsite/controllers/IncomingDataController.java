package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.models.GameDto;
import com.pulawskk.bettingsite.models.ResultDto;
import com.pulawskk.bettingsite.services.impl.HttpPostingService;
import com.pulawskk.bettingsite.services.impl.JmsHandleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/games")
public class IncomingDataController {

    private final HttpPostingService httpPostingService;
    private final JmsHandleService jmsHandleService;

    public IncomingDataController(HttpPostingService httpPostingService, JmsHandleService jmsHandleService) {
        this.httpPostingService = httpPostingService;
        this.jmsHandleService = jmsHandleService;
    }

    @PostMapping("/game")
    public String receiveGame(@RequestBody GameDto gameDto) {
        System.out.println("new game is coming: ");
        System.out.println(gameDto.getOddsH());
        System.out.println(gameDto.getOddsX());
        System.out.println(gameDto.getOddsA());
        httpPostingService.receiveGameData(gameDto);
        return "";
    }

    @PostMapping("/result")
    public String receiveResult(@RequestBody ResultDto resultDto) {
        System.out.println("new result is coming: ");
        System.out.println("Result: " + resultDto.getTeamHome() + " " + resultDto.getHomeScores() + "-"
                + resultDto.getAwayScores() + " " + resultDto.getTeamAway());
        httpPostingService.receiveResultData(resultDto);
        return "";
    }

    @GetMapping("/jms")
    public String receiveJms() throws IOException {
        jmsHandleService.receiveGameDataJms();
        jmsHandleService.receiveResultDataJms();
        return "login";
    }
}
