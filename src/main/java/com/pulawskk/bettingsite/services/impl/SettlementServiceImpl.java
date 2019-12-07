package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Bet;
import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.enums.BetStatus;
import com.pulawskk.bettingsite.enums.ResultType;
import com.pulawskk.bettingsite.services.BetService;
import com.pulawskk.bettingsite.services.GameService;
import com.pulawskk.bettingsite.services.SettlementService;
import com.pulawskk.bettingsite.utils.ResultUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettlementServiceImpl implements SettlementService, ResultUtils {

    private final BetService betService;
    private final GameService gameService;

    public SettlementServiceImpl(BetService betService, GameService gameService) {
        this.betService = betService;
        this.gameService = gameService;
    }

    @Override
    public void runBetChecking() {
        betService.findAllPrematchBets();
    }

    @Override
    public void processResultingBets(String uniqueId) {
        System.out.println("that bet is going to be resulted: " + uniqueId);
        List<Bet> bets = betService.findAllBetsByUniqueEventId(uniqueId);
        Game game = gameService.findGameById(uniqueId);
        String gameResult = eventResult1X2(game);
        bets.forEach(bet -> {
            if(bet.getType().equals(gameResult)) {
                bet.setResult(ResultType.WIN);
            } else {
                bet.setResult(ResultType.LOSE);
            }
            bet.setBetStatus(BetStatus.RESULTED);
            betService.saveBet(bet);
        });
    }

    @Scheduled(cron = "10/20 0/1 * * * ?")
    void scheduleRunning() {
        System.out.println("start checking");
        runBetChecking();
    }
}
