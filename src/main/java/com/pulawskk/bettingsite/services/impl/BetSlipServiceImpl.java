package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Bet;
import com.pulawskk.bettingsite.entities.BetLeg;
import com.pulawskk.bettingsite.entities.BetSlip;
import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.enums.BetSlipStatus;
import com.pulawskk.bettingsite.enums.BetSlipType;
import com.pulawskk.bettingsite.enums.BetStatus;
import com.pulawskk.bettingsite.enums.WalletTransactionType;
import com.pulawskk.bettingsite.exceptions.BetPlaceValidatorException;
import com.pulawskk.bettingsite.exceptions.StakePlaceValidatorException;
import com.pulawskk.bettingsite.models.Selection;
import com.pulawskk.bettingsite.repositories.BetLegRepository;
import com.pulawskk.bettingsite.repositories.BetRepository;
import com.pulawskk.bettingsite.repositories.BetSlipRepository;
import com.pulawskk.bettingsite.services.BetSlipService;
import com.pulawskk.bettingsite.services.WalletService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static java.time.LocalDateTime.*;

@Service
public class BetSlipServiceImpl implements BetSlipService {

    private final BetSlipRepository betSlipRepository;
    private final BetLegRepository betLegRepository;
    private final BetRepository betRepository;
    private final WalletService walletService;

    public BetSlipServiceImpl(BetSlipRepository betSlipRepository, BetLegRepository betLegRepository, BetRepository betRepository, WalletService walletService) {
        this.betSlipRepository = betSlipRepository;
        this.betLegRepository = betLegRepository;
        this.betRepository = betRepository;
        this.walletService = walletService;
    }

    @Override
    public BetSlip saveBetSlip(List<Selection> selections, String stake, List<BetSlipType> betSlipTypeList, User user) {
        BigDecimal currentBalance = walletService.findBalanceForUser(user.getId());
        try {
            betSlipValidation(selections, stake, currentBalance);
        } catch (RuntimeException exp) {
            System.out.println(exp.getMessage());
            return null;
        }


        BetLeg betLeg = BetLeg.builder().betLegName("first")
                .created(now()).modified(now())
                .stake(new BigDecimal(stake)).build();
        //TODO change it when list contains more that one
        BetSlipType betSlipType = betSlipTypeList.get(0);
        BetSlip betSlip = BetSlip.builder().betSlipType(betSlipType).created(now()).modified(now()).build();

        selections.forEach(selection -> {
            betLeg.addBet(Bet.builder().created(now()).modified(now())
                    .odd(new BigDecimal(selection.getValue()))
                    .selectionId(selection.getUniqueId())
                    .type(convertUserType(selection.getUserType()))
                    .betStatus(BetStatus.PREMATCH)
                    .build());
        });

        BigDecimal betLegStake = betLeg.getStake();
        BigDecimal betLegOdds = betLeg.getBets().stream()
                .map(bet -> bet.getOdd())
                .reduce(BigDecimal.ONE, BigDecimal::multiply);

        BigDecimal betLegWin = betLegStake.multiply(betLegOdds);
        betLeg.setBetLegWin(betLegWin);

        BigDecimal betSlipWin = betLegWin;
        betSlip.setBetSlipWin(betLegWin);
        betSlip.setBetSlipStatus(BetSlipStatus.ACTIVE);
        betSlip.setUser(user);

        betSlip.addBetLeg(betLeg);
        walletService.subtractBetPlaceStake(new BigDecimal(stake).doubleValue(), user.getId(), WalletTransactionType.BET_PLACE);
        BetSlip savedBetSlip = betSlipRepository.save(betSlip);

        BetLeg savedBetLeg = null;
        if(betLeg.getBetSlip() == null) {
            betLeg.setBetSlip(savedBetSlip);
            savedBetLeg = betLegRepository.save(betLeg);
        }

        BetLeg betLegForLambda = savedBetLeg;
        savedBetLeg.getBets().forEach(bet ->{
            if(bet.getBetLeg() == null) {
                bet.setBetLeg(betLegForLambda);
                betRepository.save(bet);
            }
        });

        return savedBetSlip;
    }

    void betSlipValidation(List<Selection> selections, String stake, BigDecimal currentBalance) throws BetPlaceValidatorException, StakePlaceValidatorException{
        double stakeDouble = Double.parseDouble(stake);
        if (stakeDouble < 2) {
            throw new StakePlaceValidatorException("Stake can not be less than 2!");
        }

        if (stakeDouble > currentBalance.doubleValue()) {
            throw new StakePlaceValidatorException("Stake can not be grater than you balance!");
        }

        Set<String> gamesId = new HashSet<>();
        selections.forEach(s -> gamesId.add(s.getUniqueId()));
        if (gamesId.size() < selections.size()) {
            throw new BetPlaceValidatorException();
        }
    }

    @Override
    public List<BetSlip> findAllUnresulted() {
        return betSlipRepository.findAllUnresulted();
    }

    @Override
    public BetSlip save(BetSlip betSlip) {
        return betSlipRepository.save(betSlip);
    }

    @Override
    public List<BetSlip> betSlipsActiveForUser(Long userId) {
        return betSlipRepository.findActiveBetSlipsForUser(userId);
    }

    @Override
    public List<BetSlip> betSlipsResultedForUser(Long userId) {
        return betSlipRepository.findResultedBetSlipsForUser(userId);
    }

    @Override
    public List<BetSlip> betSlipsWonForUser(Long userId) {
        return null;
    }

    @Override
    public List<BetSlip> betSlipsLostForUser(Long userId) {
        return null;
    }

    String convertUserType(String typeFromSite) {
        switch (typeFromSite) {
            case "0":
                return "1";
            case "1":
                return "X";
            case "2":
                return "2";
            default:
                return "";
        }
    }
}
