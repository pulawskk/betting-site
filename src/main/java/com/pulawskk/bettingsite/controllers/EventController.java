package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.models.*;
import com.pulawskk.bettingsite.services.UserService;
import com.pulawskk.bettingsite.services.impl.OutcomingDataServiceImpl;
import com.pulawskk.bettingsite.utils.FilterUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
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

    @GetMapping("/results/{period}")
    public String displayAllResults(@PathVariable String period, Model model) {
        List<Result> results = outcomingDataServiceImpl.prepareAllResults()
                .stream().filter(FilterUtils.filterResultsByPeriod(period)).collect(Collectors.toList());
        model.addAttribute("results", results);
        return "displayResultsDecorated";
    }

    @PostMapping("/games/search")
    public String displayAllGamesForSpecificTeam(@RequestParam(defaultValue = "") String teamName, Model model) {
        List<EventDto> eventDtos = outcomingDataServiceImpl.prepareAllEventInfoForSpecificTeam(teamName);
        model.addAttribute("eventDtos", eventDtos);
        return displayResultsEventForTeam(model);
    }

    @GetMapping("/games/search")
    public String displayResultsEventForTeam(Model model) {
        return "displayEventsForTeamDecorated";
    }


    @GetMapping("/promo")
    public String findEventsForPromotionBet(Model model) {
        model.addAttribute("betSlipPromotion", outcomingDataServiceImpl.prepareBetSlipPromotion());
        return "displayPromoBetSlipDecorated";
    }

    @GetMapping("/table/{competitionName}")
    public String prepareTableForCompetition(@PathVariable String competitionName, Model model) {
        competitionName = competitionName.contains("-") ? competitionName.replace("-", " ") : competitionName;
        List<StatisticTeamDto> statisticTeamDtos = outcomingDataServiceImpl.prepareStatisticsForCompetition(competitionName);
        model.addAttribute("statisticTeams", statisticTeamDtos);
        return "displayTableDecorated";
    }

    @GetMapping("/profit/{profitName}")
    public String prepareProfitableTeam(@PathVariable String profitName) {
        outcomingDataServiceImpl.prepareResultsForTeam(3, "Chelsea");
        return "profitTeamDecorated";
    }
}
