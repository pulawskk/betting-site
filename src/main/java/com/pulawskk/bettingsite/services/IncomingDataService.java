package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.models.GameDto;
import org.springframework.stereotype.Service;

@Service
public interface IncomingDataService {
    void receiveData(GameDto gameDto);
}
