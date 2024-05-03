package com.dorayd.sports.features.team.repositories;

import java.util.*;

import com.dorayd.sports.features.team.dto.TeamDto;
import com.dorayd.sports.features.team.mappers.TeamMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.user.mappers.UserMapper;
import com.dorayd.sports.features.user.models.User;

@Repository
public class TeamRepositoryImpl implements TeamRepository{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert teamSimpleJdbcInsert;
    private final SimpleJdbcInsert membershipSimpleJdbcInsert;

    public TeamRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.teamSimpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
            .withTableName("teams")
            .usingGeneratedKeyColumns("id");
        this.membershipSimpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
            .withTableName("user_team_memberships");
    }

    @Override
    public Optional<Team> findById(long id) {
        try {
            Team team = getTeam(id);
            team.setPlayers(getTeamPlayers(id));
            return Optional.of(team);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Team create(TeamDto newTeam) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", newTeam.name());
        
        // Save team get the generated id
        Number newId = teamSimpleJdbcInsert.executeAndReturnKey(parameters);

         // Save user memberships if players is not empty
        if(newTeam.userIds() != null && !newTeam.userIds().isEmpty()) {
            insertUserMembership(newTeam.userIds(), newId.longValue());
        }
       
        return new Team(newId.longValue(), newTeam.name(), getTeamPlayers(newId.longValue()));
    }

    @Override
    public Team update(long id, TeamDto updatedTeam) {
        final String UPDATE_BY_ID_QUERY = "UPDATE teams SET name = ? WHERE id = ?";
        jdbcTemplate.update(UPDATE_BY_ID_QUERY, updatedTeam.name(), id);
        return findById(id).orElseThrow();
    }

    @Override
    public boolean delete(long id) {
        final String DELETE_BY_ID_QUERY = "DELETE FROM teams WHERE id = ?";
        int deletedRows = jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
        return deletedRows > 0;
    }

    @Override
    public Team addPlayer(long userId, long teamId) {
        membershipSimpleJdbcInsert.execute(createTeamMembershipMapParam(teamId, userId));
        return findById(teamId).orElseThrow();
    }

    @Override
    public Team addPlayers(List<Long> userIds, long teamId) {
        insertUserMembership(userIds, teamId);
        return findById(teamId).orElseThrow();
    }

    private Team getTeam(Long teamId) {
        final String FIND_BY_ID_QUERY = "SELECT * FROM teams WHERE id = ?";
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY,
            new TeamMapper(),
            teamId
        );
    }

    private List<User> getTeamPlayers(Long teamId) {
        final String GET_ALL_PLAYER_BY_TEAM_ID = "SELECT * FROM user_team_memberships AS utm JOIN users AS u ON utm.user_id = u.id WHERE utm.team_id = ?";
        return jdbcTemplate.query(GET_ALL_PLAYER_BY_TEAM_ID, new UserMapper(), teamId);
    }

    private void insertUserMembership(List<Long> userIds, long teamId) {
        MapSqlParameterSource[] parameters = new MapSqlParameterSource[userIds.size()];
        for(int index = 0; index < userIds.size(); index++) {
            parameters[index] = createTeamMembershipMapParam(teamId, userIds.get(index));
        }
        membershipSimpleJdbcInsert.executeBatch(parameters);
    }

    private MapSqlParameterSource createTeamMembershipMapParam(long teamId, long userId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("team_id", teamId);
        parameters.put("user_id", userId);
        return new MapSqlParameterSource(parameters);
    }

    
}
