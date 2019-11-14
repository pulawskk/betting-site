package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.models.GameDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/games")
public class IncomingDataController {

    @PostMapping("/game")
    @ResponseBody
    public String receiveGame(@RequestBody GameDto gameDto) {
        System.out.println("new game is coming: ");
        System.out.println(gameDto.getOddsH());
        System.out.println(gameDto.getOddsX());
        System.out.println(gameDto.getOddsA());
        return "";
    }


}
