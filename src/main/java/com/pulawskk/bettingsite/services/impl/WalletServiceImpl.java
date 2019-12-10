package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.repositories.WalletRepository;
import com.pulawskk.bettingsite.services.WalletService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public BigDecimal addWinning(BigDecimal winningAmount) {
        return null;
    }
}
