package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.models.*;

import java.util.List;

public interface OutcomingDataService {
    List<Event> preparePrematchEvents();

    List<Result> prepareAllResults();

    List<EventDto> prepareAllEventInfoForSpecificTeam(String teamName);

    BetSlipPromotionDto prepareBetSlipPromotion();

    List<StatisticTeamDto> prepareStatisticsForCompetition(String competitionName);

    StatisticTeamDto prepareStatisticsForCompetitionForTeam(String competitionName, String teamName);
}
