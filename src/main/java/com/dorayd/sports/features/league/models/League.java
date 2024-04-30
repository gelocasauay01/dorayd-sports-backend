package com.dorayd.sports.features.league.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class League {
    private Long id;
    private String title;

    @Override
    public String toString() {
        return String.format("""
            league:
                id: %d,
                title: %s
        """, id, title);
    }
}
