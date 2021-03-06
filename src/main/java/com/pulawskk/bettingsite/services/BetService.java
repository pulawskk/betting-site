package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.entities.Bet;

import java.util.List;

public interface BetService {

    List<Bet> findAllPrematchBets();

    List<Bet> findAllResultedBets();

    List<Bet> findAllBetsByUniqueEventId(String uniqueId);

    Bet saveBet(Bet bet);
}
