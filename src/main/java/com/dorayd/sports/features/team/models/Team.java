package com.dorayd.sports.features.team.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import com.dorayd.sports.features.user.models.User;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
public class Team {

    @EqualsAndHashCode.Exclude
    private long id;
    private String name;

    @EqualsAndHashCode.Exclude
    private List<User> players;
}
