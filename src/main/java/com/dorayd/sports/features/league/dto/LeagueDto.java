package com.dorayd.sports.features.league.dto;

import java.util.List;

public record LeagueDto(String title, List<Long> teamIds) {
    
}
