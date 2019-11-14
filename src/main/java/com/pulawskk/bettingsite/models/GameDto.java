package com.pulawskk.bettingsite.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    public String getStartGame() {
        return startGame;
    }

    public void setStartGame(String startGame) {
        this.startGame = startGame;
    }

    public String getEndGame() {
        return endGame;
    }

    public void setEndGame(String endGame) {
        this.endGame = endGame;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getTeamHome() {
        return teamHome;
    }

    public void setTeamHome(String teamHome) {
        this.teamHome = teamHome;
    }

    public String getTeamAway() {
        return teamAway;
    }

    public void setTeamAway(String teamAway) {
        this.teamAway = teamAway;
    }

    public String getOddsA() {
        return oddsA;
    }

    public void setOddsA(String oddsA) {
        this.oddsA = oddsA;
    }

    public String getOddsH() {
        return oddsH;
    }

    public void setOddsH(String oddsH) {
        this.oddsH = oddsH;
    }

    public String getOddsX() {
        return oddsX;
    }

    public void setOddsX(String oddsX) {
        this.oddsX = oddsX;
    }
}
