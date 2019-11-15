package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.models.GameDto;
import org.springframework.stereotype.Service;

@Service
public interface GameService {
    Game saveGameFromDto(GameDto gameDto);
}
