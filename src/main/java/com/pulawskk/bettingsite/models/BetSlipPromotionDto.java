package com.pulawskk.bettingsite.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class BetSlipPromotionDto {

    private List<BetPromotionDto> betPromotionDtoList;
    private String course;
    private String newCourse;

    @Builder
    public BetSlipPromotionDto(List<BetPromotionDto> betPromotionDtoList, String course, String newCourse) {
        this.betPromotionDtoList = betPromotionDtoList;
        this.course = course;
        this.newCourse = newCourse;
    }
}
