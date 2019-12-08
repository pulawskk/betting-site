package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.entities.BetSlip;
import com.pulawskk.bettingsite.models.Selection;

import java.util.List;

public interface BetSlipService {

    BetSlip saveBetSlip(List<Selection> selections, String stake, List<String> betSlipTypeList);

    List<BetSlip> findAllUnresulted();

    BetSlip save(BetSlip betSlip);
}
