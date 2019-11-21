package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.enums.GameStatus;
import com.pulawskk.bettingsite.models.GameDto;
import com.pulawskk.bettingsite.models.ResultDto;
import com.pulawskk.bettingsite.services.GameService;
import com.pulawskk.bettingsite.services.IncomingDataService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HttpPostingService implements IncomingDataService {

    private final GameService gameService;

    public HttpPostingService(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void receiveGameData(GameDto gameDto) {
        StringBuilder sb = new StringBuilder();

        String gameName = sb.append(gameDto.getTeamHome())
                .append(" vs ")
                .append(gameDto.getTeamAway()).toString();

        sb.setLength(0);
        String selection = sb.append(gameDto.getOddsH())
                .append(";")
                .append(gameDto.getOddsX())
                .append(";")
                .append(gameDto.getOddsA()).toString();

        GameStatus gameStatus = null;
        switch (gameDto.getGameStatus()) {
            case "PREMATCH" :
                gameStatus = GameStatus.PREMATCH;
                break;
            case "ACTIVE" :
                gameStatus = GameStatus.ACTIVE;
                break;
            case "COMPLETED" :
                gameStatus = GameStatus.COMPLETED;
                break;
            case "RESULTED" :
                gameStatus = GameStatus.RESULTED;
                break;
            default:
                gameStatus = GameStatus.PREMATCH;
                break;
        }

        Game game = Game.builder()
                .start_date(LocalDateTime.parse(gameDto.getStartGame()))
                .end_date(LocalDateTime.parse(gameDto.getEndGame()))
                .name(gameName)
                .competition(gameDto.getCompetition())
                .selection(selection)
                .uniqueId(gameDto.getUniqueId())
                .gameStatus(gameStatus).build();

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
