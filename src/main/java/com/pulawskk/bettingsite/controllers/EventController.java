package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.models.Event;
import com.pulawskk.bettingsite.services.impl.OutcomingDataServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private OutcomingDataServiceImpl outcomingDataServiceImpl;

    public EventController(OutcomingDataServiceImpl outcomingDataServiceImpl) {
        this.outcomingDataServiceImpl = outcomingDataServiceImpl;
    }

    @GetMapping("/football")
    public String displayAvailableEvents(Model model) {
        List<Event> events = outcomingDataServiceImpl.preparePrematchEvents();
        if (!events.isEmpty()) {
            model.addAttribute("events", events);
            events.forEach(event -> {
                System.out.println("DISPLAYING events name -> " + event.getName());
            });
        }
        return "displayEvents";
    }
}
