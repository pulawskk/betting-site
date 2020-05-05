package com.pulawskk.bettingsite.entities;

import com.pulawskk.bettingsite.enums.ResultType;
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
@Table(name = "betleg")
public class BetLeg {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "new_generator")
    @SequenceGenerator(name="new_generator", sequenceName = "betleg_seq", allocationSize = 1)
    private Long id;

    @Column(name = "bet_leg_name")
    private String betLegName;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime modified;

    @ManyToOne
    private BetSlip betSlip;

    @Column(name = "stake")
    private BigDecimal stake;

    @OneToMany(mappedBy = "betLeg", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bet> bets;

    @Column(name = "bet_leg_win")
    private BigDecimal betLegWin;

    @Column(name = "result")
    @Enumerated(EnumType.STRING)
    private ResultType result;

    @Builder
    public BetLeg(Long id, String betLegName, LocalDateTime created, LocalDateTime modified, BetSlip betSlip,
                  List<Bet> bets, BigDecimal stake, BigDecimal betLegWin) {
        this.id = id;
        this.betLegName = betLegName;
        this.created = created;
        this.modified = modified;
        this.betSlip = betSlip;
        this.bets = bets;
        this.stake = stake;
        this.betLegWin = betLegWin;
    }

    public void addBet(Bet bet) {
        if(bets == null) {
            bets = new ArrayList<>();
        }
        bets.add(bet);
    }
}

