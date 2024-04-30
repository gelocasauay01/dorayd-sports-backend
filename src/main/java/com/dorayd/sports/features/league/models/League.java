package com.dorayd.sports.features.league.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
public class League {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String title;
}
