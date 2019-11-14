package com.pulawskk.bettingsite.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "betleg")
public class BetLeg {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "bet_leg_name")
    private String betLegName;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime modified;

    @ManyToOne
    private BetSlip betSlip;

    @OneToMany(mappedBy = "betLeg")
    private List<Bet> bets;
}
