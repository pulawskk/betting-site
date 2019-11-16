package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.enums.GameStatus;
import com.pulawskk.bettingsite.models.GameDto;
import com.pulawskk.bettingsite.repositories.GameRepository;
import com.pulawskk.bettingsite.services.GameService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game savePrematchGameFromDto(GameDto gameDto) {
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

        Game savedGame = gameRepository.save(game);
        return savedGame;
    }

    @Override
    @Transactional
    public void updateGameStatus(String gameStatus, String id) {
        gameRepository.updateGameStatus(gameStatus, id);
    }

    @Override
    @Transactional
    public void persistResult(String jsonResult, String uniqueId) {
        gameRepository.persistResults(jsonResult, uniqueId);
    }


}
