package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Wallet;
import com.pulawskk.bettingsite.entities.WalletAudit;
import com.pulawskk.bettingsite.enums.WalletTransactionType;
import com.pulawskk.bettingsite.repositories.WalletRepository;
import com.pulawskk.bettingsite.services.WalletAuditService;
import com.pulawskk.bettingsite.services.WalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletAuditService walletAuditService;

    public WalletServiceImpl(WalletRepository walletRepository, WalletAuditService walletAuditService) {
        this.walletRepository = walletRepository;
        this.walletAuditService = walletAuditService;
    }

    @Override
    public BigDecimal findBalanceForUser(Long userId) {
        return walletRepository.findBalanceForUser(userId);
    }

    @Override
    @Transactional
    public void updateBalance(double addAmount, Long userId, WalletTransactionType transactionType) {
        walletRepository.updateBalanceForUser(addAmount, userId);
        addAudit(addAmount, userId, transactionType);
    }

    @Override
    @Transactional
    public void subtractBetPlaceStake(double subtractAmount, Long userId, WalletTransactionType transactionType) {
        walletRepository.updateBalanceForPlaceBet(subtractAmount, userId);
        addAudit(subtractAmount, userId, transactionType);
    }

    private void addAudit(double amount, Long userId, WalletTransactionType transactionType) {
        Optional<Wallet> wallet = walletRepository.findById(userId);

        wallet.ifPresent(w -> {
            WalletAudit walletAudit = WalletAudit.builder()
                    .transactionType(transactionType)
                    .createdAt(LocalDateTime.now())
                    .amountInTransaction(new BigDecimal(String.valueOf(amount)))
                    .wallet(w)
                    .build();
            walletAuditService.saveTransaction(walletAudit);
        });
    }
}
