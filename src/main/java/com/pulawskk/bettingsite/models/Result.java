package com.pulawskk.bettingsite.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class Result {

    private String uniqueId;
    private String competition;
    private LocalDateTime endGame;
    private String name;

    @Builder
    public Result(String uniqueId, String competition, LocalDateTime endGame, String name) {
        this.uniqueId = uniqueId;
        this.competition = competition;
        this.endGame = endGame;
        this.name = name;
    }
}