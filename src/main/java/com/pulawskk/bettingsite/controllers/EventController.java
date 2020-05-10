package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.models.Event;
import com.pulawskk.bettingsite.models.Result;
import com.pulawskk.bettingsite.models.Selection;
import com.pulawskk.bettingsite.services.UserService;
import com.pulawskk.bettingsite.services.impl.OutcomingDataServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/events")
public class EventController {

    private OutcomingDataServiceImpl outcomingDataServiceImpl;
    private final UserService userService;

    public EventController(OutcomingDataServiceImpl outcomingDataServiceImpl, UserService userService) {
        this.outcomingDataServiceImpl = outcomingDataServiceImpl;
        this.userService = userService;
    }

    @GetMapping("/football/{competitionName}")
    public String displayAvailableEvents(@PathVariable String competitionName, Model model) {
        if (competitionName.contains("-")) {
            competitionName = competitionName.replaceAll("-", " ");
        }
        final String competitionNameToFilter = competitionName;

        List<Event> events;
        events = outcomingDataServiceImpl.preparePrematchEvents().stream()
                .filter(e -> e.getCompetition().equals(competitionNameToFilter)).collect(Collectors.toList());

        model.addAttribute("events", events);
        return "displayEventsDecorated";
    }

    @GetMapping("/results")
    public String displayAllResults(Model model) {
        List<Result> results = outcomingDataServiceImpl.prepareAllResults();
        if(!results.isEmpty()) {
            model.addAttribute("results", results);
            results.forEach(result -> {
                System.out.println("DISPLAYING result name -> " + result.getName());
            });
        }
        return "displayResults";
    }
}
