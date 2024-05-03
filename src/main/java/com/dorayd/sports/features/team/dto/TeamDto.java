package com.dorayd.sports.features.team.dto;

import java.util.List;

public record TeamDto(String name, List<Long> userIds) {
    
}
