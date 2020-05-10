package com.pulawskk.bettingsite.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.enums.SelectionType;
import com.pulawskk.bettingsite.models.*;
import com.pulawskk.bettingsite.services.GameService;
import com.pulawskk.bettingsite.services.OutcomingDataService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.IntStream;

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

        if(!games.isEmpty()){
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
                        .competition(game.getCompetition())
                        .uniqueId(game.getUniqueId())
                        .selections(selections)
                        .name(game.getName()).build();
                events.add(event);
            });
        }

        return events;
    }

    @Override
    public List<Result> prepareAllResults() {
        List<Result> results = new ArrayList<>();
        Set<Game> games = gameService.findAllCompletedGames();

        Gson gson = new Gson();

        if(!games.isEmpty()) {
            games.forEach(game -> {
                ResultDto resultDto = gson.fromJson(game.getResult(), ResultDto.class);
                String scoreHome = resultDto.getHomeScores();
                String scoreAway = resultDto.getAwayScores();

                String gameName = game.getName().replace("vs", scoreHome + "-" + scoreAway);

                Result result = Result.builder()
                        .competition(game.getCompetition())
                        .uniqueId(game.getUniqueId())
                        .endGame(game.getEnd_date())
                        .name(gameName).build();
                results.add(result);
            });
        }
        return results;
    }

    @Override
    public List<EventDto> prepareAllEventInfoForSpecificTeam(String teamName) {
        List<EventDto> eventDtos = new ArrayList<>();

        gameService.findAllGamesForSpecificTeam(teamName).forEach(g -> {
            String result = "N/A";

            if(g.getResult() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    ResultDto resultDto = objectMapper.readValue(g.getResult(), ResultDto.class);
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(resultDto.getHomeScores())
                                .append(" - ")
                                .append(resultDto.getAwayScores());
                    result = stringBuffer.toString();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

            EventDto eventDto = EventDto.builder()
                    .uniqueId(g.getUniqueId())
                    .competition(g.getCompetition())
                    .name(g.getName())
                    .startGame(g.getStart_date().toString())
                    .result(result)
                    .build();
            eventDtos.add(eventDto);
        });

        return eventDtos;
    }

    @Override
    public BetSlipPromotionDto prepareBetSlipPromotion() {
        List<BetPromotionDto> betPromotionDtos = new ArrayList<>();

        Set.copyOf(preparePrematchEvents()).stream().limit(2).forEach(e -> {
            Map<SelectionType, List<BigDecimal>> selections = e.getSelections();
            List<BigDecimal> odds = selections.get(SelectionType.WIN_1X2);

            String autoType = "";
            Random random = new Random();
            int randomType = random.nextInt(3);
            if (randomType == 0) {
                autoType = "Home";
            } else if (randomType == 1) {
                autoType = "Draw";
            } else if (randomType == 2) {
                autoType = "Away";
            }

            BetPromotionDto betPromotionDto = BetPromotionDto.builder()
                    .uniqueId(e.getUniqueId())
                    .competition(e.getCompetition())
                    .name(e.getName())
                    .marketType("WIN_1X2")
                    .autoType(autoType)
                    .startGame(e.getStartGame().format(DateTimeFormatter.ofPattern("dd-MM HH:mm")))
                    .odd(odds.get(randomType).toString()).build();
            betPromotionDtos.add(betPromotionDto);
        });

        BigDecimal[] course = {BigDecimal.ONE};

        betPromotionDtos.forEach(b -> {
            course[0] = course[0].multiply(new BigDecimal(b.getOdd()));
        });

        return BetSlipPromotionDto.builder().betPromotionDtoList(betPromotionDtos)
                .course(course[0].setScale(2, RoundingMode.CEILING).toString())
                .newCourse(course[0].multiply(new BigDecimal("1.15")).setScale(2, RoundingMode.CEILING).toString())
                .build();
    }
}
