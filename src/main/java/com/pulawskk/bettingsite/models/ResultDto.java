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
public class ResultDto {

    @JsonProperty("uniqueId")
    private String uniqueId;

    @JsonProperty("homeCorners")
    private String homeCorners;

    @JsonProperty("awayCorners")
    private String awayCorners;

    @JsonProperty("homeScores")
    private String homeScores;

    @JsonProperty("homeOffsides")
    private String homeOffsides;

    @JsonProperty("homeRedCards")
    private String homeRedCards;

    @JsonProperty("homeYellowCards")
    private String homeYellowCards;

    @JsonProperty("teamHome")
    private String teamHome;

    @JsonProperty("awayScores")
    private String awayScores;

    @JsonProperty("awayOffsides")
    private String awayOffsides;

    @JsonProperty("awayRedCards")
    private String awayRedCards;

    @JsonProperty("awayYellowCards")
    private String awayYellowCards;

    @JsonProperty("teamAway")
    private String teamAway;

    @Builder
    public ResultDto(String uniqueId, String homeCorners, String awayCorners, String homeScores,
                     String homeOffsides, String homeRedCards, String homeYellowCards, String teamHome,
                     String awayScores, String awayOffsides, String awayRedCards, String awayYellowCards, String teamAway) {
        this.uniqueId = uniqueId;
        this.homeCorners = homeCorners;
        this.awayCorners = awayCorners;
        this.homeScores = homeScores;
        this.homeOffsides = homeOffsides;
        this.homeRedCards = homeRedCards;
        this.homeYellowCards = homeYellowCards;
        this.teamHome = teamHome;
        this.awayScores = awayScores;
        this.awayOffsides = awayOffsides;
        this.awayRedCards = awayRedCards;
        this.awayYellowCards = awayYellowCards;
        this.teamAway = teamAway;
    }
}

