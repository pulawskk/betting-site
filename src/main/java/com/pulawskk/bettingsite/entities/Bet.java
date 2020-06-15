package com.pulawskk.bettingsite.entities;

import com.pulawskk.bettingsite.enums.BetStatus;
import com.pulawskk.bettingsite.enums.ResultType;
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
@Table(name = "bet")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "new_generator")
    @SequenceGenerator(name="new_generator", sequenceName = "bet_seq", allocationSize = 1)

    private Long id;

    @Column(name = "selection_id")
    private String selectionId;

    @Column(name = "type")
    private String type;

    @Column(name = "odd")
    private BigDecimal odd;

    @Column(name = "result")
    @Enumerated(EnumType.STRING)
    private ResultType result;

    @Enumerated(EnumType.STRING)
    @Column(name = "bet_status")
    private BetStatus betStatus;

    @ManyToOne
    private BetLeg betLeg;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Builder
    public Bet(Long id, String selectionId, String type, BigDecimal odd, BetLeg betLeg, LocalDateTime created, LocalDateTime modified, BetStatus betStatus, ResultType resultType) {
        this.result = resultType;
        this.id = id;
        this.selectionId = selectionId;
        this.type = type;
        this.odd = odd;
        this.betLeg = betLeg;
        this.created = created;
        this.modified = modified;
        this.betStatus = betStatus;
    }
}
