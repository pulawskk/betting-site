package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.entities.BetLeg;

import java.util.List;

public interface BetLegService {

    List<BetLeg> findAllUnresulted();

    BetLeg save(BetLeg betLeg);
}
