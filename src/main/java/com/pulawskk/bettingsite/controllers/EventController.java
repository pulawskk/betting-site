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
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private OutcomingDataServiceImpl outcomingDataServiceImpl;
    private final UserService userService;

    public EventController(OutcomingDataServiceImpl outcomingDataServiceImpl, UserService userService) {
        this.outcomingDataServiceImpl = outcomingDataServiceImpl;
        this.userService = userService;
    }

    @GetMapping("/football")
    public String displayAvailableEvents(Model model, Selection selection) {
        List<Event> events = outcomingDataServiceImpl.preparePrematchEvents();
        if (!events.isEmpty()) {
            model.addAttribute("events", events);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        model.addAttribute("currentUser", currentUser);
        return "displayEvents";
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
