package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Bet;
import com.pulawskk.bettingsite.repositories.BetRepository;
import com.pulawskk.bettingsite.services.BetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetServiceImpl implements BetService {

    private final Logger logger = LoggerFactory.getLogger(BetServiceImpl.class);

    private final BetRepository betRepository;

    public BetServiceImpl(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    @Override
    public List<Bet> findAllPrematchBets() {
        long start = System.currentTimeMillis();
        List<Bet> prematchBets = betRepository.findAllPrematchBets();
        long end = System.currentTimeMillis();

        logger.info("[" + getClass().getSimpleName()
                + "] method: findAllPrematchBets, number of prematch bets: "
                + prematchBets.size() + " found in " + (end - start) + " ms.");
        return prematchBets;
    }

    @Override
    public List<Bet> findAllResultedBets() {
        List<Bet> resultedBets = betRepository.findAllResultedBets();

        return resultedBets;
    }

    @Override
    public List<Bet> findAllBetsByUniqueEventId(String uniqueId) {
        List<Bet> bets = betRepository.findAllBySelectionId(uniqueId);
        return bets;
    }

    @Override
    public Bet saveBet(Bet bet) {
        return betRepository.save(bet);
    }


}
