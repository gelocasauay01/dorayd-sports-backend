package com.dorayd.sports.features.game.repositories;

import com.dorayd.sports.features.game.dto.GameDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class GameRepositoryImpl implements GameRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public GameRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
            .withTableName("games")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<GameDto> findById(Long id) {
        try {
            final String FIND_BY_ID_QUERY = "SELECT * FROM games WHERE id = ?";
            GameDto gameDto = jdbcTemplate.queryForObject(
                    FIND_BY_ID_QUERY,
                    (rs, rowNum) -> new GameDto(
                            rs.getLong("id"),
                            rs.getLong("league_id"),
                            rs.getLong("team_a_id"),
                            rs.getLong("team_b_id"),
                            dateToLocalDateTime(rs.getTimestamp("schedule"))
                    ),
                    id
            );
            return Optional.ofNullable(gameDto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public GameDto create(GameDto input) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("league_id", input.leagueId());
        parameters.put("team_a_id", input.teamAId());
        parameters.put("team_b_id", input.teamBId());
        parameters.put("schedule", input.schedule());
        Number gameId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return input.withId(gameId.longValue());
    }

    @Override
    public GameDto updateSchedule(Long id, LocalDateTime schedule) {
        jdbcTemplate.update("UPDATE games SET schedule = ? WHERE id = ?", schedule, id);
        return findById(id).orElseThrow();
    }

    @Override
    public boolean delete(Long id) {
        int deleteCount = jdbcTemplate.update("DELETE FROM games WHERE id = ?", id);
        return deleteCount > 0;
    }

    private LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
