package com.pulawskk.bettingsite.models;

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
public class BetSlipDto {

    private List<Selection> selections;
    private BigDecimal stake;
    private List<BetSlipType> betSlipTypeList;

    @Builder
    public BetSlipDto(List<Selection> selections, BigDecimal stake, List<BetSlipType> betSlipTypeList) {
        this.selections = selections;
        this.stake = stake;
        this.betSlipTypeList = betSlipTypeList;
    }
}
