package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.models.*;
import com.pulawskk.bettingsite.services.OutcomingDataService;
import com.pulawskk.bettingsite.services.impl.OutcomingDataServiceImpl;
import com.pulawskk.bettingsite.services.impl.UserServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    @InjectMocks
    EventController eventController;

    @Mock
    OutcomingDataServiceImpl outcomingDataService;

    @Mock
    UserServiceImpl userService;

    MockMvc mockMvc;

    List<Event> eventList;
    List<Result> resultList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
        Event event = Event.builder()
                .name("Chelsea vs Arsenal")
                .competition("Premier League")
                .startGame(now())
                .uniqueId("321asd")
                .build();
        eventList = Lists.newArrayList(event);

        Result result = Result.builder()
                .competition("Premier League")
                .uniqueId("333abc")
                .endGame(now())
                .build();

        resultList = Lists.newArrayList(result);
    }

    @Test
    void shouldPassListOfAllAvailableEvents_whenCompetitionNameIsPassed() throws Exception {
        //given
        final String competitionName = "Premier-League";
        when(outcomingDataService.preparePrematchEvents()).thenReturn(eventList);

        //when
        mockMvc.perform(get("/events/football/" + competitionName))
                .andExpect(model().attributeExists("events"))
                .andExpect(status().isOk())
                .andExpect(view().name("displayEventsDecorated"));

        //then
        verify(outcomingDataService, times(1)).preparePrematchEvents();
    }

    @Test
    void displayAllResults() throws Exception {
        //given
        Result yesterdayResult = Result.builder()
                .competition("Premier League")
                .uniqueId("444abc")
                .endGame(now().minusDays(1))
                .build();
        resultList.add(yesterdayResult);

        final String period = "yesterday";
        when(outcomingDataService.prepareAllResults()).thenReturn(resultList);

        //when
        mockMvc.perform(get("/events/results/" + period))
                .andExpect(model().attributeExists("results"))
                .andExpect(status().isOk())
                .andExpect(view().name("displayResultsDecorated"));

        //then
        verify(outcomingDataService, times(1)).prepareAllResults();
    }

    @Test
    void shouldPassListOfAllEventDto_whenSpecificTeamNameIsPassed() throws Exception {
        //given
        EventDto eventDto = EventDto.builder()
                .competition("Premier League")
                .uniqueId("444abc")
                .name("Chelsea vs Arsenal")
                .build();

        List<EventDto> eventDtoList = Lists.newArrayList(eventDto);

        final String teamName = "Chelsea";
        when(outcomingDataService.prepareAllEventInfoForSpecificTeam(teamName)).thenReturn(eventDtoList);

        //when
        mockMvc.perform(post("/events/games/search")
                    .param("teamName", "Chelsea"))
                .andExpect(model().attributeExists("eventDtos"))
                .andExpect(status().isOk())
                .andExpect(view().name("displayEventsForTeamDecorated"));

        //then
        verify(outcomingDataService, times(1)).prepareAllEventInfoForSpecificTeam(teamName);
    }

    @Test
    void shouldGenerateView_whenDisplayResultsEventForTeamIsCalled() throws Exception {
        mockMvc.perform(get("/events/games/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("displayEventsForTeamDecorated"));
    }

    @Test
    void shouldPassListOfEventsDto_whenFindEventsForPromotionBetIsCalled() throws Exception {
        //given
        BetSlipPromotionDto betSlipPromotionDto = BetSlipPromotionDto.builder().build();
        when(outcomingDataService.prepareBetSlipPromotion()).thenReturn(betSlipPromotionDto);

        //when
        mockMvc.perform(get("/events/promo"))
                .andExpect(model().attributeExists("betSlipPromotion"))
                .andExpect(status().isOk())
                .andExpect(view().name("displayPromoBetSlipDecorated"));

        //then
        verify(outcomingDataService, times(1)).prepareBetSlipPromotion();
    }

    @Test
    void shouldPassListOfStatisticTeamDto_whenCompetitionTableIsPrepared() throws Exception {
        //given
        final String competitionNameWithDash = "Premier-League";
        final String competitionNameWithoutDash = "Premier League";
        StatisticTeamDto statisticTeamDto = StatisticTeamDto.builder().build();
        List<StatisticTeamDto> statisticTeamDtos = Lists.newArrayList(statisticTeamDto);

        when(outcomingDataService.prepareStatisticsForCompetition(competitionNameWithoutDash)).thenReturn(statisticTeamDtos);

        //when
        mockMvc.perform(get("/events/table/" + competitionNameWithDash))
                .andExpect(model().attributeExists("statisticTeams"))
                .andExpect(status().isOk())
                .andExpect(view().name("displayTableDecorated"));

        //then
        verify(outcomingDataService, times(1)).prepareStatisticsForCompetition(competitionNameWithoutDash);
    }
}