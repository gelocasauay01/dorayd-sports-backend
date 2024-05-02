package com.dorayd.sports.features.game.models;

import com.dorayd.sports.features.league.models.League;
import com.dorayd.sports.features.team.models.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class Game {
    @EqualsAndHashCode.Exclude
    private Long id;
    private League league;
    private Team teamA;
    private Team teamB;
    private LocalDateTime schedule;
}
