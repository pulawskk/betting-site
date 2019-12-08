package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.models.GameDto;
import com.pulawskk.bettingsite.models.ResultDto;

public interface IncomingDataService {
    void receiveGameData(GameDto gameDto);

    void receiveResultData(ResultDto resultDto);
}
