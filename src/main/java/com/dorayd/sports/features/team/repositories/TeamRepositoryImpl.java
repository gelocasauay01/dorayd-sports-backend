package com.dorayd.sports.features.team.repositories;

import java.util.*;

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
    public Optional<Team> findById(Long id) {
        try {
            Team team = getTeam(id);
            team.setPlayers(getTeamPlayers(id));
            return Optional.of(team);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Team create(Team newTeam) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", newTeam.getName());
        
        // Save team get the generated id
        Number newId = teamSimpleJdbcInsert.executeAndReturnKey(parameters);
        newTeam.setId(newId.longValue());

         // Save user memberships if players is not empty
        if(newTeam.getPlayers() != null && !newTeam.getPlayers().isEmpty()) {
            insertUserMembership(newTeam.getPlayers(), newTeam.getId());
        }
       
        return newTeam;
    }

    @Override
    public Team update(Long id, Team updatedTeam) {
        final String UPDATE_BY_ID_QUERY = "UPDATE teams SET name = ? WHERE id = ?";
        jdbcTemplate.update(UPDATE_BY_ID_QUERY, updatedTeam.getName(), id);
        return findById(id).orElseThrow();
    }

    @Override
    public boolean delete(Long id) {
        final String DELETE_BY_ID_QUERY = "DELETE FROM teams WHERE id = ?";
        int deletedRows = jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
        return deletedRows > 0;
    }

    @Override
    public Team addPlayer(Long userId, Long teamId) {
        membershipSimpleJdbcInsert.execute(createTeamMembershipMapParam(teamId, userId));
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

    private void insertUserMembership(List<User> users, Long teamId) {
        MapSqlParameterSource[] parameters = new MapSqlParameterSource[users.size()];
        for(int index = 0; index < users.size(); index++) {
            User user = users.get(index);
            parameters[index] = createTeamMembershipMapParam(teamId, user.getId());
        }
        membershipSimpleJdbcInsert.executeBatch(parameters);
    }

    private MapSqlParameterSource createTeamMembershipMapParam(Long teamId, Long userId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("team_id", teamId);
        parameters.put("user_id", userId);
        return new MapSqlParameterSource(parameters);
    }
}
