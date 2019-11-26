package com.pulawskk.bettingsite.models;

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
}
