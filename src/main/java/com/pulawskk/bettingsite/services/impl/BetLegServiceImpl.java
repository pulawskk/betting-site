package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.BetLeg;
import com.pulawskk.bettingsite.repositories.BetLegRepository;
import com.pulawskk.bettingsite.services.BetLegService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetLegServiceImpl implements BetLegService {

    private final BetLegRepository betLegRepository;

    public BetLegServiceImpl(BetLegRepository betLegRepository) {
        this.betLegRepository = betLegRepository;
    }

    @Override
    public List<BetLeg> findAllUnresulted() {
        return betLegRepository.findAllUnresulted();
    }

    @Override
    public BetLeg save(BetLeg betLeg) {
        return betLegRepository.save(betLeg);
    }
}
