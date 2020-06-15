package com.pulawskk.bettingsite.entities;

import com.pulawskk.bettingsite.enums.WalletTransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "wallet_audit")
public class WalletAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "new_generator")
    @SequenceGenerator(name="new_generator", sequenceName = "wallet_audit_seq", allocationSize = 1)
    private Long id;

    @Column(name = "amount_transaction")
    private BigDecimal amountInTransaction;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private WalletTransactionType transactionType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    private Wallet wallet;

    @Builder
    public WalletAudit(Long id, BigDecimal amountInTransaction, WalletTransactionType transactionType, LocalDateTime createdAt, Wallet wallet) {
        this.id = id;
        this.amountInTransaction = amountInTransaction;
        this.transactionType = transactionType;
        this.createdAt = createdAt;
        this.wallet = wallet;
    }
}
