package com.pulawskk.bettingsite.entities;

import com.pulawskk.bettingsite.enums.BetType;
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
}