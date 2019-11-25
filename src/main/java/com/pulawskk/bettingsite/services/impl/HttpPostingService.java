package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.models.GameDto;
import com.pulawskk.bettingsite.models.ResultDto;
import com.pulawskk.bettingsite.services.GameService;
import com.pulawskk.bettingsite.services.IncomingDataService;
import com.pulawskk.bettingsite.utils.GameUtils;
import org.springframework.stereotype.Service;


@Service
public class HttpPostingService implements IncomingDataService, GameUtils {

    private final GameService gameService;

    public HttpPostingService(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void receiveGameData(GameDto gameDto) {
        Game game = processGameFromGameDto(gameDto);
        gameService.savePrematchGame(game);
    }

    @Override
    public void receiveResultData(ResultDto resultDto) {
        final String jsonResultString = processResultFromResultDto(resultDto);
        final String uniqueId = resultDto.getUniqueId();
        final String statusToUpdate = "COMPLETED";

        gameService.persistResult(jsonResultString, uniqueId);
        gameService.updateGameStatus(statusToUpdate, uniqueId);
    }
}
