package com.pulawskk.bettingsite.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;

@Setter
@Getter
@NoArgsConstructor
public class StatisticTeamDto {

    private int totalCorners;
    private int totalPoints;
    private int totalGoalsScored;
    private int totalGoalsConceded;
    private int totalOffsides;
    private int totalRedCards;
    private int totalYellowCards;
    private String teamName;
    private String competitionName;
    private int gamesPlayed;

    @Builder
    public StatisticTeamDto(int gamesPlayed, int totalPoints, int totalCorners, int totalGoalsScored, int totalGoalsConceded, int totalOffsides, int totalRedCards, int totalYellowCards, String teamName, String competitionName) {
        this.totalCorners = totalCorners;
        this.totalGoalsScored = totalGoalsScored;
        this.totalGoalsConceded = totalGoalsConceded;
        this.totalOffsides = totalOffsides;
        this.totalRedCards = totalRedCards;
        this.totalYellowCards = totalYellowCards;
        this.teamName = teamName;
        this.competitionName = competitionName;
        this.totalPoints = totalPoints;
        this.gamesPlayed = gamesPlayed;
    }


//    public int compare(StatisticTeamDto o2) {
//        if (this.getTotalPoints() > o2.getTotalPoints()) {
//            return 1;
//        } else if (this.getTotalPoints() < o2.getTotalPoints()) {
//            return -1;
//        }
//        return 0;
//    }
}

