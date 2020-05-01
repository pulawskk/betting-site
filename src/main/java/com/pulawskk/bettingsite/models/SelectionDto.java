package com.pulawskk.bettingsite.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SelectionDto {

    private String uniqueId;
    private String marketType;
    private String odd;
    private String userType;
    private String eventName;
    private String competition;

    @Override
    public String toString() {
        return "uniqueId='" + uniqueId + '\'' +
                ", marketType='" + marketType + '\'' +
                ", odd='" + odd + '\'' +
                ", userType='" + userType + '\'' +
                ", eventName='" + eventName + '\'' +
                ", competition='" + competition + '\'' +
                '}';
    }
}
