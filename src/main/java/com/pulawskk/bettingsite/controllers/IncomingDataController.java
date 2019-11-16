package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.models.GameDto;
import com.pulawskk.bettingsite.models.ResultDto;
import com.pulawskk.bettingsite.services.impl.HttpPostingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/games")
public class IncomingDataController {

    private final HttpPostingService httpPostingService;

    public IncomingDataController(HttpPostingService httpPostingService) {
        this.httpPostingService = httpPostingService;
    }

    @PostMapping("/game")
    @ResponseBody
    public String receiveGame(@RequestBody GameDto gameDto) {
        System.out.println("new game is coming: ");
        System.out.println(gameDto.getOddsH());
        System.out.println(gameDto.getOddsX());
        System.out.println(gameDto.getOddsA());
        httpPostingService.receiveGameData(gameDto);
        return "";
    }

    @PostMapping("/result")
    @ResponseBody
    public String receiveResult(@RequestBody ResultDto resultDto) {
        System.out.println("new result is coming: ");
        System.out.println("Result: " + resultDto.getTeamHome() + " " + resultDto.getHomeScores() + "-"
                + resultDto.getAwayScores() + " " + resultDto.getTeamAway());
        httpPostingService.receiveResultData(resultDto);
        return "";
    }
}
