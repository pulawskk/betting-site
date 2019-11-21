package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.repositories.GameRepository;
import com.pulawskk.bettingsite.services.GameService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void savePrematchGame(Game game) {
        gameRepository.save(game);
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

    @Override
    public Set<Game> findAllPrematchGames() {
        return gameRepository.findAllByGameStatusPrematch();
    }

    @Override
    public Set<Game> findAllCompletedGames() {
        return gameRepository.findAllByGameStatusCompleted();
    }


}
