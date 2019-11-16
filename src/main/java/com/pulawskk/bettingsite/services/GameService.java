package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.enums.GameStatus;
import com.pulawskk.bettingsite.models.GameDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface GameService {
    Game savePrematchGameFromDto(GameDto gameDto);

    void updateGameStatus(String gameStatus, String uniqueId);

    void persistResult(String jsonResult, String uniqueId);

    Set<Game> findAllPrematchGames();
}
