package com.pulawskk.bettingsite.entities;

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
@Table(name = "bet")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "selection_id")
    private String selectionId;

    @Column(name = "type")
    private String type;

    @Column(name = "stake")
    private BigDecimal stake;

    @Column(name = "odd")
    private BigDecimal odd;

    @ManyToOne
    private BetLeg betLeg;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime modified;


}
