package com.pulawskk.bettingsite.entities;

import com.pulawskk.bettingsite.enums.BetType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "betslip")
public class BetSlip {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "bet_type")
    private BetType betType;

    @OneToMany(mappedBy = "betSlip")
    @Column(name = "bet_legs")
    private List<BetLeg> betLegs;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime modified;

    public BetSlip() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BetType getBetType() {
        return betType;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    public List<BetLeg> getBetLegs() {
        return betLegs;
    }

    public void setBetLegs(List<BetLeg> betLegs) {
        this.betLegs = betLegs;
    }
}
