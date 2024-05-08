package com.dorayd.sports.features.game_stats.models;

import java.util.List;

import com.dorayd.sports.features.user.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PlayerStats {
    private User user;
    private List<GameStats> gameStats;
}
