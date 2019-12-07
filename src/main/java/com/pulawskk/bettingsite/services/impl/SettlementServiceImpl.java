package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.services.BetService;
import com.pulawskk.bettingsite.services.SettlementService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SettlementServiceImpl implements SettlementService {

    private final BetService betService;

    public SettlementServiceImpl(BetService betService) {
        this.betService = betService;
    }

    @Override
    public void runBetChecking() {
        betService.findAllPrematchBets();
    }

    @Scheduled(cron = "10/20 0/1 * * * ?")
    void scheduleRunning() {
        System.out.println("start checking");
        runBetChecking();
    }
}
