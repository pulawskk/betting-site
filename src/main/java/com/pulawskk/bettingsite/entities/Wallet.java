package com.pulawskk.bettingsite.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "wallet_wallet_audit",
                joinColumns = @JoinColumn(name = "wallet_id"),
                inverseJoinColumns = @JoinColumn(name = "wallet_audit_id"))
    private List<WalletAudit> walletAudits;

    @Builder
    public Wallet(Long id, BigDecimal balance, LocalDateTime modified, User user) {
        this.id = id;
        this.balance = balance;
        this.modified = modified;
        this.user = user;
    }
}
