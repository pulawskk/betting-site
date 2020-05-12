package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.entities.User;

import javax.transaction.Transactional;
import java.math.BigDecimal;

public interface WalletService {

    BigDecimal findBalanceForUser(Long userId);

    void updateBalance(double addAmount, Long userId);

    void subtractBetPlaceStake(double subtractAmount, Long userId);
}
