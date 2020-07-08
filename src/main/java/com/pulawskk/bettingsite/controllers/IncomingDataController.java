package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.models.GameDto;
import com.pulawskk.bettingsite.models.ResultDto;
import com.pulawskk.bettingsite.services.impl.HttpPostingService;
import com.pulawskk.bettingsite.services.impl.JmsHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/games")
public class IncomingDataController {

    private final Logger logger = LoggerFactory.getLogger(IncomingDataController.class);

    private final HttpPostingService httpPostingService;
    private final JmsHandleService jmsHandleService;

    public IncomingDataController(HttpPostingService httpPostingService, JmsHandleService jmsHandleService) {
        this.httpPostingService = httpPostingService;
        this.jmsHandleService = jmsHandleService;
    }

    @CrossOrigin
    @PostMapping("/game")
    public String receiveGame(@RequestBody GameDto gameDto) {
        logger.info("[" + getClass().getSimpleName() + "] method: receiveGame, new game is coming with id: " + gameDto.getUniqueId());
        httpPostingService.receiveGameData(gameDto);
        return "";
    }

    @PostMapping("/result")
    public String receiveResult(@RequestBody ResultDto resultDto) {
        logger.info("[" + getClass().getSimpleName() + "] method: receiveGame, new game is coming for game with id: " + resultDto.getUniqueId());
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
