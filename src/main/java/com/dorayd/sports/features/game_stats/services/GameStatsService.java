package com.dorayd.sports.features.game_stats.services;

import com.dorayd.sports.features.game_stats.dto.GameStatsDto;
import com.dorayd.sports.features.game_stats.models.GameStats;
import com.dorayd.sports.features.game_stats.models.PlayerStats;

public interface GameStatsService {
    GameStats create(GameStatsDto newStats);
    PlayerStats getByUserIdAndGameId(long userId, long gameId);
    GameStats updateByUserIdAndGameId(GameStatsDto updatedStats);
    boolean deleteByUserIdAndGameId(long userId, long gameId);
}
