package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.entities.BetSlip;
import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.enums.BetSlipType;
import com.pulawskk.bettingsite.models.Selection;

import java.util.List;

public interface BetSlipService {

    BetSlip saveBetSlip(List<Selection> selections, String stake, List<BetSlipType> betSlipTypeList, User user);

    List<BetSlip> findAllUnresulted();

    BetSlip save(BetSlip betSlip);
}
