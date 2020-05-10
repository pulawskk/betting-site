package com.pulawskk.bettingsite.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BetPromotionDto {

    private String uniqueId;
    private String competition;
    private String name;
    private String marketType;
    private String autoType;
    private String odd;
    private String startGame;

    @Builder
    public BetPromotionDto(String uniqueId, String competition, String name, String marketType, String autoType, String odd, String startGame) {
        this.uniqueId = uniqueId;
        this.competition = competition;
        this.name = name;
        this.marketType = marketType;
        this.autoType = autoType;
        this.odd = odd;
        this.startGame = startGame;
    }
}
