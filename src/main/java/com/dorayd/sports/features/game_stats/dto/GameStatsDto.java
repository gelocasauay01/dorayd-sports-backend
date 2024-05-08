package com.dorayd.sports.features.game_stats.dto;

import com.dorayd.sports.features.game_stats.models.GameStats;

public record GameStatsDto(int userId, int gameId, int points, int assists, int rebounds, int blocks, int steals, int turnovers, int fouls) {
    public GameStats toGameStats() {
        return GameStats.builder()
            .points(points)
            .assists(assists)
            .rebounds(rebounds)
            .steals(steals)
            .blocks(blocks)
            .fouls(fouls)
            .turnovers(turnovers)
            .build();
    }
}
