package com.pulawskk.bettingsite.utils;

import com.pulawskk.bettingsite.models.Result;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public final class FilterUtils {
    private static Predicate<Result> filterTodayResults = result ->
            (result.getEndGame().getDayOfYear() == LocalDateTime.now().getDayOfYear())
                    && (result.getEndGame().getYear() == LocalDateTime.now().getYear());

    private static Predicate<Result> filterYesterdayResults = result ->
            (result.getEndGame().getDayOfYear() == LocalDateTime.now().minusDays(1).getDayOfYear())
                    && (result.getEndGame().getYear() == LocalDateTime.now().getYear());

    private static Predicate<Result> filterLastWeekResults = result ->
            (result.getEndGame().getDayOfYear() > LocalDateTime.now().minusDays(7).getDayOfYear())
                    && (result.getEndGame().getYear() == LocalDateTime.now().getYear());

    public static Predicate<Result> filterResultsByPeriod(String period) {
        Predicate<Result> predicate = null;
        switch (period) {
            case "today":
                predicate = filterTodayResults;
                break;
            case "yesterday":
                predicate = filterYesterdayResults;
                break;
            case "lastWeek":
                predicate = filterLastWeekResults;
                break;
        }
        if (predicate == null) {
            throw new RuntimeException("Predicate can not be established");
        }
        return predicate;
    }

    public static Predicate<Result> filterResultsByCompetitionPremierLeague = result ->
            result.getCompetition().equals("Premier League");

}
