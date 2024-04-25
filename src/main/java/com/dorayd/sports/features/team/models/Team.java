package com.dorayd.sports.features.team.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Team {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return String.format("""
            team:
                id: %d
                name: %s    
        """, id, name);
    }
}
