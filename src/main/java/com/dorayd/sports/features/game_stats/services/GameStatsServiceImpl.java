package com.dorayd.sports.features.game_stats.services;

import org.springframework.stereotype.Service;

import com.dorayd.sports.features.game_stats.dto.GameStatsDto;
import com.dorayd.sports.features.game_stats.models.GameStats;
import com.dorayd.sports.features.game_stats.models.PlayerStats;
import com.dorayd.sports.features.game_stats.repositories.GameStatsRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class GameStatsServiceImpl implements GameStatsService{

    private final GameStatsRepository repository;

    @Override
    public GameStats create(GameStatsDto newStats) {
        log.info("Creating game stats: {}", newStats);
        return repository.create(newStats);
    }

    @Override
    public PlayerStats getByUserIdAndGameId(long userId, long gameId) {
        log.info("Getting game stats with user ID {} and game ID {}", userId, gameId);
        return repository.getByUserIdAndGameId(userId, gameId);
    }

    @Override
    public GameStats updateByUserIdAndGameId(GameStatsDto updatedStats) { 
        log.info("Updating game stats with {}", updatedStats);
        return repository.updateByUserIdAndGameId(updatedStats);
    }

    @Override
    public boolean deleteByUserIdAndGameId(long userId, long gameId) {
        log.info("Deleting game stats with user ID {} and game ID {}", userId, gameId);
        return repository.deleteByUserIdAndGameId(userId, gameId);
    }
    
}
