package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.entities.WalletAudit;
import com.pulawskk.bettingsite.enums.WalletTransactionType;

import javax.transaction.Transactional;
import java.math.BigDecimal;

public interface WalletService {

    BigDecimal findBalanceForUser(Long userId);

    void updateBalance(double addAmount, Long userId, WalletTransactionType transactionType);

    void subtractBetPlaceStake(double subtractAmount, Long userId, WalletTransactionType transactionType);
}
