package com.pulawskk.bettingsite.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "new_generator")
    @SequenceGenerator(name="new_generator", sequenceName = "wallet_seq", allocationSize = 1)
    private Long id;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "modified")
    private LocalDateTime modified;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "wallet")
    private List<WalletAudit> walletAudits;

    @Builder
    public Wallet(Long id, BigDecimal balance, LocalDateTime modified, User user) {
        this.id = id;
        this.balance = balance;
        this.modified = modified;
        this.user = user;
    }

    public void addWalletAudit(WalletAudit walletAudit) {
        if (walletAudits == null) {
            walletAudits = new ArrayList<>();
        }
        walletAudits.add(walletAudit);
    }
}
