package com.dorayd.sports.features.team.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.user.mappers.UserMapper;
import com.dorayd.sports.features.user.models.User;
import com.dorayd.sports.features.user.repositories.UserRepository;

import io.jsonwebtoken.lang.Collections;

@Repository
public class TeamRepositoryImpl implements TeamRepository{

    private final String FIND_BY_ID_QUERY = "SELECT * FROM teams WHERE id = ?";
    private final String DELETE_BY_ID_QUERY = "DELETE FROM teams WHERE id = ?";
    private final String UPDATE_BY_ID_QUERY = "UPDATE teams SET name = ? WHERE id = ?";
    private final String GET_ALL_PLAYER_ID = "SELECT * FROM user_team_memberships AS utm JOIN users AS u ON utm.user_id = u.id WHERE utm.team_id = ?";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert teamSimpleJdbcInsert;
    private SimpleJdbcInsert membershipSimpleJdbcInsert;

    public TeamRepositoryImpl(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
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
        jdbcTemplate.update(UPDATE_BY_ID_QUERY, updatedTeam.getName(), id);
        updatedTeam.setId(id);
        return updatedTeam;
    }

    @Override
    public boolean delete(Long id) {
        int deletedRows = jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
        return deletedRows > 0;
    }

    @Override
    public Team addPlayer(User user, Long teamId) {
        membershipSimpleJdbcInsert.execute(createTeamMembershipParameters(teamId, user.getId()));
        return findById(teamId).orElseThrow();
    }

    private Team getTeam(Long teamId) { 
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY,
            (rs, rowNum) -> new Team(
                rs.getLong("id"),
                rs.getString("name"),
                Collections.emptyList()
            ),
            teamId
        );
    }

    private List<User> getTeamPlayers(Long id) {
        return jdbcTemplate.query(GET_ALL_PLAYER_ID, new UserMapper(), id);
    }

    private void insertUserMembership(List<User> users, Long teamId) {

        @SuppressWarnings("unchecked")
        Map<String, Object> parameters[] = (Map<String, Object>[]) new Map[users.size()];

        for(int index = 0; index < users.size(); index++) {
            parameters[index] = createTeamMembershipParameters(teamId, users.get(index).getId());
        }
        membershipSimpleJdbcInsert.executeBatch(parameters);
    }

    private Map<String, Object> createTeamMembershipParameters(Long teamId, Long userId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("team_id", teamId);
        parameters.put("user_id", userId);
        return parameters;
    }
}
