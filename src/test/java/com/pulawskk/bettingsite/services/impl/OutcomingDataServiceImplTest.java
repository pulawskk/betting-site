package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.models.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static java.time.LocalDateTime.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OutcomingDataServiceImplTest {

    @InjectMocks
    OutcomingDataServiceImpl outcomingDataService;

    @Mock
    GameServiceImpl gameService;

    List<Event> eventList;
    Event event;
    Game game;
    Set<Game> gameSet;
    List<Game> gameList;

    @BeforeEach
    void setUp() {
        game = Game.builder()
                .selection("1.3;5.74;7.15")
                .start_date(now())
                .competition("Premier League")
                .uniqueId("abc1234")
                .name("Chelsea vs Arsenal")
                .build();
        gameSet = new HashSet<>();
        gameList = Lists.newArrayList();

        event = Event.builder()
                .competition("Premier League")
                .name("Chelsea vs Arsenal")
                .startGame(now())
                .uniqueId("abc1234")
                .build();

        eventList = Lists.newArrayList(event);
    }

    @Test
    void shouldReturnListOfEvents_whenPreparePrematchEventMethodIsCalled() {
        //given
        gameSet.add(game);
        when(gameService.findAllPrematchGames()).thenReturn(gameSet);

        //when
        List<Event> actualEventList = outcomingDataService.preparePrematchEvents();

        //then
        verify(gameService, times(1)).findAllPrematchGames();
        assertThat(actualEventList.size(), is(gameSet.size()));
    }

    @Test
    void prepareAllResults() {
        //given
        final String result = "{\"homeRedCards\":\"1\",\"homeScores\":\"8\"," +
                "\"awayRedCards\":\"3\",\"awayYellowCards\":\"3\",\"homeYellowCards\":\"6\"," +
                "\"homeOffsides\":\"7\",\"awayOffsides\":\"1\",\"awayCorners\":\"8\"," +
                "\"homeCorners\":\"9\",\"awayScores\":\"2\"}";
        game.setResult(result);
        gameSet.add(game);
        when(gameService.findAllCompletedGames()).thenReturn(gameSet);

        //when
        List<Result> actualResultList = outcomingDataService.prepareAllResults();

        //then
        verify(gameService, times(1)).findAllCompletedGames();
        assertThat(actualResultList.size(), is(gameSet.size()));
    }

    @Test
    void shouldReturnListOfEventDto_whenTeamNameIsPassedToPrepareAllEventsInfoMethod() {
        //given
        final String teamName = "Chelsea";
        gameList.add(game);
        when(gameService.findAllGamesForSpecificTeam(teamName)).thenReturn(gameList);

        //when
        List<EventDto> actualEventDtoList = outcomingDataService.prepareAllEventInfoForSpecificTeam(teamName);

        //then
        verify(gameService, times(1)).findAllGamesForSpecificTeam(teamName);
        assertThat(actualEventDtoList.size(), is(gameList.size()));
    }

    @Test
    void shouldReturnBetSlipPromotionDto_whenPrepareBetSlipPromotionMethodIsCalled() {
        //given
        Game secondGame = Game.builder()
                .selection("1.3;5.74;7.15")
                .start_date(now())
                .competition("Premier League")
                .uniqueId("bbb1234")
                .name("Chelsea vs Derby")
                .build();

        Game thirdGame = Game.builder()
                .selection("1.3;5.74;7.15")
                .start_date(now())
                .competition("Premier League")
                .uniqueId("ddd1234")
                .name("Chelsea vs West Ham")
                .build();
        gameSet.add(game);
        gameSet.add(secondGame);
        gameSet.add(thirdGame);
        when(gameService.findAllPrematchGames()).thenReturn(gameSet);

        //when
        BetSlipPromotionDto actualBetSlipPromotionDto = outcomingDataService.prepareBetSlipPromotion();

        //then
        verify(gameService, times(1)).findAllPrematchGames();
        assertThat(actualBetSlipPromotionDto.getBetPromotionDtoList().size(), is(not(greaterThan(2))));
    }

    @Test
    void shouldReturnListOfStatisticTeamDto_whenPrepareStatisticsForCompetitionMethodIsCalled() {
        //given
        final String competitionName = "Premier League";

        //when
        List<StatisticTeamDto> actualStatisticTeamDtoList = outcomingDataService.prepareStatisticsForCompetition(competitionName);

        //then
        verify(gameService, times(20)).findAllCompletedGames();
    }

    @Test
    void shouldReturnStatisticTeamDto_whenPrepareStatisticsForCompetitionForTeamIsCalled() {
        //given
        final String competitionName = "Premier League";
        final String teamName = "Chelsea";
        final String result = "{\"homeRedCards\":\"1\",\"homeScores\":\"8\"," +
                "\"awayRedCards\":\"3\",\"awayYellowCards\":\"3\",\"homeYellowCards\":\"6\"," +
                "\"homeOffsides\":\"7\",\"awayOffsides\":\"1\",\"awayCorners\":\"8\"," +
                "\"homeCorners\":\"9\",\"awayScores\":\"2\"}";

        game.setResult(result);

        gameSet.add(game);
        when(gameService.findAllCompletedGames()).thenReturn(gameSet);

        //when
        StatisticTeamDto actualStatisticTeamDto = outcomingDataService.prepareStatisticsForCompetitionForTeam(competitionName, teamName);

        //then
        verify(gameService, times(1)).findAllCompletedGames();
        assertThat(actualStatisticTeamDto.getCompetitionName(), is(competitionName));
        assertThat(actualStatisticTeamDto.getTeamName(), is(teamName));
    }
}