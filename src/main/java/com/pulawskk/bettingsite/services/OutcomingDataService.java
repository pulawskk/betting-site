package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.models.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OutcomingDataService {
    List<Event> preparePrematchEvents();
}
