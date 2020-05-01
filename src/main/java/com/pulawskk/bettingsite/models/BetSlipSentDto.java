package com.pulawskk.bettingsite.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pulawskk.bettingsite.enums.BetSlipType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class BetSlipSentDto {

//    private List<Selection> selections;
//    private BigDecimal stake;

//    @JsonProperty
//    private List<BetSlipType> types;

    @JsonProperty
    private String type;

    public BetSlipSentDto(String type) {
        this.type = type;
    }
}
