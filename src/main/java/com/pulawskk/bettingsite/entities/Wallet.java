package com.pulawskk.bettingsite.entities;

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
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "new_generator")
    @SequenceGenerator(name="new_generator", sequenceName = "wallet_seq", initialValue = 10)
    private Long id;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "modified")
    private LocalDateTime modified;

    @OneToOne
    private User user;

    @Builder
    public Wallet(Long id, BigDecimal balance, LocalDateTime modified, User user) {
        this.id = id;
        this.balance = balance;
        this.modified = modified;
        this.user = user;
    }
}
