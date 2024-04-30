package com.dorayd.sports.features.team.mappers;

import com.dorayd.sports.features.team.models.Team;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public class TeamMapper implements RowMapper<Team> {

    @Override
    public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Team(rs.getLong("id"), rs.getString("name"), Collections.emptyList());
    }
}
