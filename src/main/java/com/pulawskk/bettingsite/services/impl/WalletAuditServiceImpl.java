package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.WalletAudit;
import com.pulawskk.bettingsite.repositories.WalletAuditRepository;
import com.pulawskk.bettingsite.services.WalletAuditService;
import org.springframework.stereotype.Service;

@Service
public class WalletAuditServiceImpl implements WalletAuditService {
    private final WalletAuditRepository walletAuditRepository;

    public WalletAuditServiceImpl(WalletAuditRepository walletAuditRepository) {
        this.walletAuditRepository = walletAuditRepository;
    }

    @Override
    public WalletAudit saveTransaction(WalletAudit walletAudit) {
        return walletAuditRepository.save(walletAudit);
    }
}
