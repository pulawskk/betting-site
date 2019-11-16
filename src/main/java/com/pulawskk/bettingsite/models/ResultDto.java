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
public class ResultDto {
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

}

