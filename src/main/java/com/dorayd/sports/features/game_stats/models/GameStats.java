package com.dorayd.sports.features.game_stats.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class GameStats {
    private int points;
    private int assists;
    private int rebounds;
    private int blocks;
    private int steals;
    private int turnovers;
    private int fouls;
}
