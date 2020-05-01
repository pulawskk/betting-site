package com.pulawskk.bettingsite.models;

import com.pulawskk.bettingsite.enums.BetSlipType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SelectionDto {

    private String uniqueId;
    private String marketType;
    private String odd;
    private String userType;

    @Override
    public String toString() {
        return "uniqueId='" + uniqueId + '\'' +
                ", marketType='" + marketType + '\'' +
                ", odd='" + odd + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
