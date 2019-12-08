package com.pulawskk.bettingsite.services;

public interface SettlementService {

    void runBetLegChecking();

    void runBetSlipChecking();

    void processResultingBets(String uniqueId);
}
