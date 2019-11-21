package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.enums.GameStatus;
import com.pulawskk.bettingsite.models.GameDto;
import com.pulawskk.bettingsite.models.ResultDto;
import com.pulawskk.bettingsite.services.GameService;
import com.pulawskk.bettingsite.services.IncomingDataService;
import com.pulawskk.bettingsite.utils.GameUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        final String uniqueId = resultDto.getUniqueId();
        final String statusToUpdate = "COMPLETED";

        JSONObject jsonResult = new JSONObject();
	    jsonResult.put("homeCorners", resultDto.getHomeCorners());
		jsonResult.put("awayCorners", resultDto.getAwayCorners());
		jsonResult.put("homeScores", resultDto.getHomeScores());
		jsonResult.put("homeOffsides", resultDto.getHomeOffsides());
		jsonResult.put("homeRedCards", resultDto.getHomeRedCards());
		jsonResult.put("homeYellowCards",resultDto.getHomeYellowCards());
		jsonResult.put("awayScores", resultDto.getAwayScores());
		jsonResult.put("awayOffsides", resultDto.getAwayOffsides());
		jsonResult.put("awayRedCards", resultDto.getAwayRedCards());
		jsonResult.put("awayYellowCards", resultDto.getAwayYellowCards());

		String jsonResultString = jsonResult.toString();

        gameService.persistResult(jsonResultString, uniqueId);
        gameService.updateGameStatus(statusToUpdate, uniqueId);
    }
}
