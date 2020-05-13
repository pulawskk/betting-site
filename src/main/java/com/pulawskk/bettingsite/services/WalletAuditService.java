package com.pulawskk.bettingsite.services;

import com.pulawskk.bettingsite.entities.WalletAudit;

import java.util.List;

public interface WalletAuditService {
    WalletAudit saveTransaction(WalletAudit walletAudit);

    List<WalletAudit> getTransactionsForWallet(Long walletId);
}
