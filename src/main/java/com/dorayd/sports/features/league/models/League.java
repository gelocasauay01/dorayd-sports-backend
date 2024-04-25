package com.dorayd.sports.features.league.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
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
