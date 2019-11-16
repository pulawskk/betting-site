package com.pulawskk.bettingsite.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameDto {
    @JsonProperty("startGame")
    private String startGame;

    @JsonProperty("endGame")
    private String endGame;

    @JsonProperty("gameStatus")
    private String gameStatus;

    @JsonProperty("teamHome")
    private String teamHome;

    @JsonProperty("teamAway")
    private String teamAway;

    @JsonProperty("oddsA")
    private String oddsA;

    @JsonProperty("oddsH")
    private String oddsH;

    @JsonProperty("oddsX")
    private String oddsX;

    @Builder
    public GameDto(String startGame, String endGame, String gameStatus,
                   String teamHome, String teamAway,
                   String oddsA, String oddsH, String oddsX) {
        this.startGame = startGame;
        this.endGame = endGame;
        this.gameStatus = gameStatus;
        this.teamHome = teamHome;
        this.teamAway = teamAway;
        this.oddsA = oddsA;
        this.oddsH = oddsH;
        this.oddsX = oddsX;
    }
}