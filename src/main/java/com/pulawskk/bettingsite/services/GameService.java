package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.entities.Game;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface GameService {
    void savePrematchGame(Game game);

    void updateGameStatus(String gameStatus, String uniqueId);

    void persistResult(String jsonResult, String uniqueId);

    Set<Game> findAllPrematchGames();

    Set<Game> findAllCompletedGames();
}
