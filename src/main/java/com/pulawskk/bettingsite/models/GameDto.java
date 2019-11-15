package com.pulawskk.bettingsite.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
}