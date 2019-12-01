package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.entities.BetSlip;
import com.pulawskk.bettingsite.models.Selection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BetSlipService {

    BetSlip saveBetSlip(List<Selection> selections);
}
