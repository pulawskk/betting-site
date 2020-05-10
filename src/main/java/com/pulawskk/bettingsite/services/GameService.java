package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.entities.Game;

import java.util.List;
import java.util.Set;

public interface GameService {
    void savePrematchGame(Game game);

    void updateGameStatus(String gameStatus, String uniqueId);

    void persistResult(String jsonResult, String uniqueId);

    Set<Game> findAllPrematchGames();

    Set<Game> findAllCompletedGames();

    Game findGameById(String uniqueId);

    List<Game> findAllGamesForSpecificTeam(String teamName);
}
