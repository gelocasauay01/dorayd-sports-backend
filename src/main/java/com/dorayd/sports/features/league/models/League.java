package com.dorayd.sports.features.league.models;

import com.dorayd.sports.features.team.models.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@AllArgsConstructor
@Data
public class League {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String title;

    private List<Team> teams;
}
