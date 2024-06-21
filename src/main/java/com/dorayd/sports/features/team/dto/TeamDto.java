package com.dorayd.sports.features.team.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TeamDto(
        @NotNull(message = "Team name cannot be null")
        @Size(min = 3, max = 100, message = "Team name must have 3-100 characters")
        String name,

        List<Long> userIds) {
    
}
