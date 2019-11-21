package com.pulawskk.bettingsite.utils;

import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.enums.GameStatus;
import com.pulawskk.bettingsite.models.GameDto;

import java.time.LocalDateTime;

public interface GameUtils {

    default Game processGameFromGameDto(GameDto gameDto) {
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
        return game;
    }
}
