package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Bet;
import com.pulawskk.bettingsite.entities.BetLeg;
import com.pulawskk.bettingsite.entities.BetSlip;
import com.pulawskk.bettingsite.enums.BetSlipType;
import com.pulawskk.bettingsite.models.Selection;
import com.pulawskk.bettingsite.repositories.BetSlipRepository;
import com.pulawskk.bettingsite.services.BetSlipService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static java.time.LocalDateTime.*;

@Service
public class BetSlipServiceImpl implements BetSlipService {

    private final BetSlipRepository betSlipRepository;

    public BetSlipServiceImpl(BetSlipRepository betSlipRepository) {
        this.betSlipRepository = betSlipRepository;
    }

    @Override
    public BetSlip saveBetSlip(List<Selection> selections) {
        BetLeg betLeg = BetLeg.builder().betLegName("first").created(now()).modified(now()).build();
        BetSlipType betSlipType = selections.size() > 0 ? BetSlipType.MULTIPLE : BetSlipType.SINGLE;
        BetSlip betSlip = BetSlip.builder().betSlipType(betSlipType).created(now()).modified(now()).build();

        selections.forEach(selection -> {
            betLeg.addBet(Bet.builder().created(now()).modified(now())
                    .odd(new BigDecimal(selection.getValue()))
                    .selectionId(selection.getUniqueId())
                    .type(convertUserType(selection.getUserType()))
                    .build());
        });

        betSlip.addBetLeg(betLeg);
        BetSlip savedBetSlip = betSlipRepository.save(betSlip);
        return savedBetSlip;
    }

    private String convertUserType(String typeFromSite) {
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
