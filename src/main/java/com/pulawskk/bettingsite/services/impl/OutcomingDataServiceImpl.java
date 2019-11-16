package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.enums.SelectionType;
import com.pulawskk.bettingsite.models.Event;
import com.pulawskk.bettingsite.services.GameService;
import com.pulawskk.bettingsite.services.OutcomingDataService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OutcomingDataServiceImpl implements OutcomingDataService {

    private final GameService gameService;

    public OutcomingDataServiceImpl(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public List<Event> preparePrematchEvents() {
        List<Event> events = new ArrayList<>();
        Set<Game> games = gameService.findAllPrematchGames();

        games.forEach(game -> {
            Map<SelectionType, List<BigDecimal>> selections = new HashMap<>();

            String selectionString = game.getSelection();
            String[] oddsFromString = selectionString.split(";");

            List<BigDecimal> odds = new ArrayList<>();
            for(String odd : oddsFromString) {
                odds.add(new BigDecimal(odd));
            }

            selections.put(SelectionType.WIN_1X2, odds);

            Event event = Event.builder()
                                .startGame(game.getStart_date())
                                .uniqueId(game.getUniqueId())
                                .selections(selections)
                                .name(game.getName()).build();
            events.add(event);
        });

        return events;
    }
}
