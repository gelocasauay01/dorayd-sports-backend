package com.dorayd.sports.features.league.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record LeagueDto(
        @NotNull(message = "League name cannot be null")
        @Size(min = 3, max = 100, message = "League name must have 3-100 characters")
        String title,

        List<Long> teamIds) {
    
}
