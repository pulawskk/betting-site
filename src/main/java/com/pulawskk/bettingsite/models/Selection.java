package com.pulawskk.bettingsite.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Selection {
    private String marketType;
    private String value;
    private String marketName;
    private String uniqueId;
    private String competition;
    private String userType;

    @Override
    public String toString() {
        return uniqueId + " | " + marketName + " | "
                + competition + " | " + marketType + " | "
                + userType + " | " + value;
    }

    @Builder
    public Selection(String marketType, String value, String marketName, String uniqueId, String competition, String userType) {
        this.marketType = marketType;
        this.value = value;
        this.marketName = marketName;
        this.uniqueId = uniqueId;
        this.competition = competition;
        this.userType = userType;
    }
}
