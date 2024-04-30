package com.dorayd.sports.features.league.repositories;

import java.util.*;

import com.dorayd.sports.features.team.mappers.TeamMapper;
import com.dorayd.sports.features.team.models.Team;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.dorayd.sports.features.league.models.League;

@Repository
public class LeagueRepositoryImpl implements LeagueRepository{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert leagueSimpleJdbcInsert;
    private final SimpleJdbcInsert memberSimpleJdbcInsert;

    public LeagueRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.leagueSimpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
            .withTableName("leagues")
            .usingGeneratedKeyColumns("id");
        this.memberSimpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
            .withTableName("team_league_memberships");
    }

    @Override
    public Optional<League> findById(Long id) {
        try {
            final League league = getLeague(id);
            league.setTeams(getTeams(id));
            return Optional.of(league);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public League create(League newLeague) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", newLeague.getTitle());
        final Number newId = leagueSimpleJdbcInsert.executeAndReturnKey(parameters);
        newLeague.setId(newId.longValue());
        return newLeague;
    }

    @Override
    public League update(Long id, League updatedLeague) {
        final String UPDATE_BY_ID_QUERY = "UPDATE leagues SET title = ? WHERE id = ?";
        jdbcTemplate.update(UPDATE_BY_ID_QUERY, updatedLeague.getTitle(), id);
        return findById(id).orElseThrow();
    }

    @Override
    public boolean delete(Long id) {
        final String DELETE_BY_ID_QUERY = "DELETE FROM leagues WHERE id = ?";
        final int deletedRows = jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
        return deletedRows > 0;
    }

    @Override
    public League addTeam(Long teamId, Long leagueId) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("team_id", teamId);
        parameters.put("league_id", leagueId);
        memberSimpleJdbcInsert.execute(parameters);
        return findById(leagueId).orElseThrow();
    }

    private League getLeague(Long leagueId) {
        final String FIND_BY_ID_QUERY = "SELECT * FROM leagues WHERE id = ?";
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY,
                (rs, rowNum) -> new League(
                        rs.getLong("id"),
                        rs.getString("title"),
                        new ArrayList<>()
                )
                , leagueId
        );
    }

    private List<Team> getTeams(Long leagueId) {
        final String GET_TEAMS_BYL_LEAGUE_ID = "SELECT * FROM team_league_memberships AS tlm JOIN teams AS t ON tlm.team_id = t.id WHERE tlm.league_id = ?";
        return jdbcTemplate.query(GET_TEAMS_BYL_LEAGUE_ID, new TeamMapper(), leagueId);
    }

}
