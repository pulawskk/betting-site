package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.repositories.WalletRepository;
import com.pulawskk.bettingsite.services.WalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public BigDecimal findBalanceForUser(Long userId) {
        return walletRepository.findBalanceForUser(userId);
    }

    @Override
    @Transactional
    public void updateBalance(double addAmount, Long userId) {
        walletRepository.updateBalanceForUser(addAmount, userId);
    }

    @Override
    @Transactional
    public void subtractBetPlaceStake(double subtractAmount, Long userId) {
        walletRepository.updateBalanceForPlaceBet(subtractAmount, userId);
    }
}
