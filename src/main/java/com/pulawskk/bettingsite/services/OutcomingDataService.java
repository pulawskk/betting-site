package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.models.*;

import java.util.List;

public interface OutcomingDataService {
    List<Event> preparePrematchEvents();

    List<Result> prepareAllResults();

    List<EventDto> prepareAllEventInfoForSpecificTeam(String teamName);

    BetSlipPromotionDto prepareBetSlipPromotion();
}
