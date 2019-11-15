package com.pulawskk.bettingsite.entities;

import com.pulawskk.bettingsite.enums.GameStatus;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

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
}