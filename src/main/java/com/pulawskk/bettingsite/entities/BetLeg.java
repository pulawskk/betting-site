package com.pulawskk.bettingsite.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "betleg")
public class BetLeg {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "new_generator")
    @SequenceGenerator(name="new_generator", sequenceName = "betleg_seq", initialValue = 101)
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
