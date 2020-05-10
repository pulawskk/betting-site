package com.pulawskk.bettingsite.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EventDto {

    private String name;
    private String uniqueId;
    private String competition;
    private String startGame;
    private String result;

    @Builder
    public EventDto(String name, String uniqueId, String competition, String startGame, String result) {
        this.name = name;
        this.uniqueId = uniqueId;
        this.competition = competition;
        this.startGame = startGame;
        this.result = result;
    }
}
