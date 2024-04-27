package com.dorayd.sports.features.team.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.dorayd.sports.features.user.models.User;

@AllArgsConstructor
@Getter
@Setter
public class Team {
    private Long id;
    private String name;
    private List<User> players;

    @Override
    public String toString() {
        return String.format("""
            team:
                id: %d
                name: %s    
                players: %s
        """, id, name, players);
    }
}
