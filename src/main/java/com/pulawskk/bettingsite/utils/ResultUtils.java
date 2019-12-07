package com.pulawskk.bettingsite.utils;

import com.google.gson.Gson;
import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.models.Result;
import com.pulawskk.bettingsite.models.ResultDto;

public interface ResultUtils {

    default String eventResult1X2(Game game) {
        Gson gson = new Gson();
        ResultDto resultDto = gson.fromJson(game.getResult(), ResultDto.class);
        Integer homeScore = Integer.parseInt(resultDto.getHomeScores());
        Integer awayScore = Integer.parseInt(resultDto.getAwayScores());

        if(homeScore > awayScore) {
            return "1";
        } else if(homeScore < awayScore) {
            return "2";
        } else {
            return "X";
        }
    }
}
