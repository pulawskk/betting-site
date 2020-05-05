package com.pulawskk.bettingsite.entities;

import com.pulawskk.bettingsite.enums.GameStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "new_generator")
    @SequenceGenerator(name="new_generator", sequenceName = "game_seq", allocationSize = 1)
    private Long id;

    @Column(name = "unique_id")
    private String uniqueId;

    @Column(name = "selection")
    private String selection;

    @Column(name = "competition")
    private String competition;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDateTime start_date;

    @Column(name = "end_date")
    private LocalDateTime end_date;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_status")
    private GameStatus gameStatus;

    @Column(name = "result")
    private String result;

    @Builder
    public Game(Long id, String selection, String competition,
                LocalDateTime end_date, LocalDateTime start_date,
                String name, GameStatus gameStatus, String uniqueId) {
        this.id = id;
        this.selection = selection;
        this.competition = competition;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.gameStatus = gameStatus;
        this.uniqueId = uniqueId;
    }
}