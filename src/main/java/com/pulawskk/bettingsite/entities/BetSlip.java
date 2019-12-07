package com.pulawskk.bettingsite.entities;

import com.pulawskk.bettingsite.enums.BetSlipType;
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
@Table(name = "betslip")
public class BetSlip {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "new_generator")
    @SequenceGenerator(name="new_generator", sequenceName = "betslip_seq", initialValue = 101)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "bet_slip_type")
    private BetSlipType betSlipType;

    @OneToMany(mappedBy = "betSlip", cascade = CascadeType.ALL)
    @Column(name = "bet_legs")
    private List<BetLeg> betLegs;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "bet_slip_win")
    private BigDecimal betSlipWin;

    @Builder
    public BetSlip(Long id, BetSlipType betSlipType, List<BetLeg> betLegs, LocalDateTime created,
                   LocalDateTime modified, BigDecimal betSlipWin) {
        this.id = id;
        this.betSlipType = betSlipType;
        this.betLegs = betLegs;
        this.created = created;
        this.modified = modified;
        this.betSlipWin = betSlipWin;
    }

    public void addBetLeg(BetLeg betLeg) {
        if (betLegs == null) {
            betLegs = new ArrayList<>();
        }
        betLegs.add(betLeg);
    }
}