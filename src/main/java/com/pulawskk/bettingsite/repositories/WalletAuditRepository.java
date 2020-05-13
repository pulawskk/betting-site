package com.pulawskk.bettingsite.repositories;

import com.pulawskk.bettingsite.entities.Wallet;
import com.pulawskk.bettingsite.entities.WalletAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface WalletAuditRepository extends JpaRepository<WalletAudit, Long> {

}
