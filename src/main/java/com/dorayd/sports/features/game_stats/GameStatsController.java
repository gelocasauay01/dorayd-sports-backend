package com.dorayd.sports.features.game_stats;

import org.springframework.web.bind.annotation.RestController;

import com.dorayd.sports.features.game_stats.dto.GameStatsDto;
import com.dorayd.sports.features.game_stats.models.GameStats;
import com.dorayd.sports.features.game_stats.models.PlayerStats;
import com.dorayd.sports.features.game_stats.services.GameStatsService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@AllArgsConstructor
@RestController
@RequestMapping(GameStatsController.GAME_STATS_API_URL)
public class GameStatsController {
    public static final String GAME_STATS_API_URL = "/api/game-stats";

    private final GameStatsService service;

    @PostMapping
    public ResponseEntity<GameStats> create(@RequestBody GameStatsDto newStats) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(service.create(newStats));
    }

    @GetMapping
    public ResponseEntity<PlayerStats> getByUserIdAndGameId(@RequestParam long userId, @RequestParam long gameId) {
        return ResponseEntity.ok(service.getByUserIdAndGameId(userId, gameId));
    }
    
    @PutMapping
    public ResponseEntity<GameStats> updateByUserIdAndGameId(@RequestBody GameStatsDto updatedGameStats) {
        return ResponseEntity
            .ok(service.updateByUserIdAndGameId(updatedGameStats));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByUserIdAndGameId(@RequestParam long userId, @RequestParam long gameId) {
        return ResponseEntity.ok(service.deleteByUserIdAndGameId(userId, gameId));
    }
}
