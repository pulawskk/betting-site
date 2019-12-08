package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Bet;
import com.pulawskk.bettingsite.entities.BetLeg;
import com.pulawskk.bettingsite.entities.BetSlip;
import com.pulawskk.bettingsite.entities.Game;
import com.pulawskk.bettingsite.enums.BetSlipStatus;
import com.pulawskk.bettingsite.enums.BetStatus;
import com.pulawskk.bettingsite.enums.ResultType;
import com.pulawskk.bettingsite.services.*;
import com.pulawskk.bettingsite.utils.ResultUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class SettlementServiceImpl implements SettlementService, ResultUtils {

    private final BetService betService;
    private final BetLegService betLegService;
    private final BetSlipService betSlipService;
    private final GameService gameService;

    public SettlementServiceImpl(BetService betService, BetLegService betLegService, BetSlipService betSlipService, GameService gameService) {
        this.betService = betService;
        this.betLegService = betLegService;
        this.betSlipService = betSlipService;
        this.gameService = gameService;
    }

    @Transactional
    @Override
    public void runBetLegChecking() {
        List<BetLeg> betLegList = betLegService.findAllUnresulted();
        betLegList.forEach(betLeg -> {
            List<Bet> bets = betLeg.getBets();

            if(isAnyLostBet(bets)) {
               betLeg.setResult(ResultType.LOSE);
               System.out.println("betleg is lost");
            }

            if(isAnyUnresultedBet(bets)) {
                System.out.println("betleg is still unresulted");
                return;
            }

            if(isAllBetsWin(bets)) {
                betLeg.setResult(ResultType.WIN);
                System.out.println("betleg is win");
            }
            betLegService.save(betLeg);
        });
    }

    private Boolean isAnyUnresultedBet(List<Bet> bets) {
        return bets.stream()
                .map(Bet::getResult)
                .anyMatch(Objects::isNull);
    }

    private Boolean isAnyLostBet(List<Bet> bets) {
        return bets.stream()
                .anyMatch(b -> ResultType.LOSE.equals(b.getResult()));
    }

    private Boolean isAllBetsWin(List<Bet> bets) {
        return bets.stream()
                .allMatch(b -> ResultType.WIN.equals(b.getResult()));
    }

    @Transactional
    @Override
    public void runBetSlipChecking() {
        List<BetSlip> betSlips = betSlipService.findAllUnresulted();
        betSlips.forEach(betSlip -> {
            List<BetLeg> betLegs = betSlip.getBetLegs();

            if(isAnyLostBetLeg(betLegs)) {
                betSlip.setResult(ResultType.LOSE);
                betSlip.setBetSlipStatus(BetSlipStatus.RESULTED);
                System.out.println("bet slip is lost");
            }

            if(isAnyUnresultedBetLeg(betLegs)) {
                System.out.println("bet slip is still unresulted");
                return;
            }

            if(isAllBetLegsWin(betLegs)) {
                betSlip.setResult(ResultType.WIN);
                betSlip.setBetSlipStatus(BetSlipStatus.RESULTED);
                System.out.println("bet slip is win");
            }
            betSlipService.save(betSlip);
        });
    }

    private boolean isAnyUnresultedBetLeg(List<BetLeg> betLegs) {
        return betLegs.stream()
                .map(BetLeg::getResult)
                .anyMatch(Objects::isNull);
    }

    private boolean isAnyLostBetLeg(List<BetLeg> betLegs) {
        return betLegs.stream()
                .anyMatch(bl -> ResultType.LOSE.equals(bl.getResult()));
    }

    private boolean isAllBetLegsWin(List<BetLeg> betLegs) {
        return betLegs.stream()
                .allMatch(bl -> ResultType.WIN.equals(bl.getResult()));
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

    @Scheduled(cron = "45 0/1 * * * ?")
    void scheduleRunning() {
        System.out.println("start checking");
        runBetLegChecking();
        runBetSlipChecking();
    }
}
