package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.models.Event;
import com.pulawskk.bettingsite.models.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OutcomingDataService {
    List<Event> preparePrematchEvents();

    List<Result> prepareAllResults();
}
