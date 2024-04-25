package com.dorayd.sports.features.team.repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.dorayd.sports.features.team.models.Team;

@Repository
public class TeamRepositoryImpl implements TeamRepository{

    private final String FIND_BY_ID_QUERY = "SELECT * FROM teams WHERE id = ?";
    private final String DELETE_BY_ID_QUERY = "DELETE FROM teams WHERE id = ?";
    private final String UPDATE_BY_ID_QUERY = "UPDATE teams SET name = ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TeamRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
            .withTableName("teams")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Team> findById(Long id) {
        try {
            Team team = jdbcTemplate.queryForObject(FIND_BY_ID_QUERY,
                (rs, rowNum) -> new Team(
                    rs.getLong("id"),
                    rs.getString("name")
                ),
                id
            );
            return Optional.of(team);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Team create(Team newTeam) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", newTeam.getName());
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        newTeam.setId(newId.longValue());
        return newTeam;
    }

    @Override
    public Team update(Long id, Team updatedTeam) {
        jdbcTemplate.update(UPDATE_BY_ID_QUERY, updatedTeam.getName(), id);
        updatedTeam.setId(id);
        return updatedTeam;
    }

    @Override
    public boolean delete(Long id) {
        int deletedRows = jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
        return deletedRows > 0;
    }
    
}
