package com.pulawskk.bettingsite.services;

public interface SettlementService {

    void runBetLegChecking();

    void processResultingBets(String uniqueId);
}
