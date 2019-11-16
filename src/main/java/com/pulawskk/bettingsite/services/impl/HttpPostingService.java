package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.models.GameDto;
import com.pulawskk.bettingsite.models.ResultDto;
import com.pulawskk.bettingsite.services.GameService;
import com.pulawskk.bettingsite.services.IncomingDataService;
import org.springframework.stereotype.Service;

@Service
public class HttpPostingService implements IncomingDataService {

    private final GameService gameService;

    public HttpPostingService(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void receiveGameData(GameDto gameDto) {
        gameService.savePrematchGameFromDto(gameDto);
    }

    @Override
    public void receiveResultData(ResultDto resultDto) {
        gameService.updateGameStatus("COMPLETED", 3L);
    }

}
