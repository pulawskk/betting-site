package com.pulawskk.bettingsite.models;

import com.pulawskk.bettingsite.enums.BetSlipType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class BetSlipSentDto {

    private List<BetSlipType> types;

    private String stake;

    private List<SelectionDto> selections;

}
