package com.dorayd.sports.features.league.repositories;

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.dorayd.sports.features.league.models.League;

@Repository
public class LeagueRepositoryImpl implements LeagueRepository{

    private final String FIND_BY_ID_QUERY = "SELECT * FROM leagues WHERE id = ?";
    private final String DELETE_BY_ID_QUERY = "DELETE FROM leagues WHERE id = ?";
    private final String UPDATE_BY_ID_QUERY = "UPDATE leagues SET title = ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public LeagueRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
            .withTableName("leagues")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<League> findById(Long id) {
        try {
            League league = jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, 
            (rs, rowNum) -> new League(
                    rs.getLong("id"),
                    rs.getString("title")
                )   
            ,
            id);
            return Optional.of(league);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public League create(League newLeague) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", newLeague.getTitle());
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        newLeague.setId(newId.longValue());
        return newLeague;
    }

    @Override
    public League update(Long id, League updatedLeague) {
        jdbcTemplate.update(UPDATE_BY_ID_QUERY, updatedLeague.getTitle(), id);
        updatedLeague.setId(id);
        return updatedLeague;
    }

    @Override
    public boolean delete(Long id) {
        int deletedRows = jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
        return deletedRows > 0;
    }
    
}
