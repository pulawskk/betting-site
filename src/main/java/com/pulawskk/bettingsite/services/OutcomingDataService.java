package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.models.Event;
import com.pulawskk.bettingsite.models.Result;

import java.util.List;

public interface OutcomingDataService {
    List<Event> preparePrematchEvents();

    List<Result> prepareAllResults();
}
