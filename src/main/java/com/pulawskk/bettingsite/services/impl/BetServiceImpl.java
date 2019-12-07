package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Bet;
import com.pulawskk.bettingsite.repositories.BetRepository;
import com.pulawskk.bettingsite.services.BetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetServiceImpl implements BetService {

    private final BetRepository betRepository;

    public BetServiceImpl(BetRepository betRepository) {
        this.betRepository = betRepository;
    }


    @Override
    public List<Bet> findAllPrematchBets() {
        List<Bet> prematchBets = betRepository.findAllPrematchBets();
        System.out.println("number of prematch bets: " + prematchBets.size());
        return prematchBets;
    }


}
