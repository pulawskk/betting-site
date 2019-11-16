package com.pulawskk.bettingsite.models;

import com.pulawskk.bettingsite.enums.SelectionType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
public class Event {

    private String name;
    private String uniqueId;
    private String competition;
    private LocalDateTime startGame;
    private Map<SelectionType, List<BigDecimal>> selections = new HashMap<>();

    @Builder
    public Event(String name, String uniqueId, String competition, LocalDateTime startGame, Map<SelectionType, List<BigDecimal>> selections) {
        this.name = name;
        this.uniqueId = uniqueId;
        this.competition = competition;
        this.startGame = startGame;
        this.selections = selections;
    }
}
