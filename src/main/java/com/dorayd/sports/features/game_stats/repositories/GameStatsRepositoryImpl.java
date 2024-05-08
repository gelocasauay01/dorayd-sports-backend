package com.dorayd.sports.features.game_stats.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.dorayd.sports.features.game_stats.dto.GameStatsDto;
import com.dorayd.sports.features.game_stats.models.GameStats;
import com.dorayd.sports.features.game_stats.models.PlayerStats;
import com.dorayd.sports.features.user.models.User;
import com.dorayd.sports.features.user.repositories.UserRepository;

@Repository
public class GameStatsRepositoryImpl implements GameStatsRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final UserRepository userRepository;

    public GameStatsRepositoryImpl(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
            .withTableName("game_stats");
    }

    @Override
    public GameStats create(GameStatsDto newStats) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", newStats.userId());
        parameters.put("game_id", newStats.gameId());
        parameters.put("points", newStats.points());
        parameters.put("assists", newStats.assists());
        parameters.put("rebounds", newStats.rebounds());
        parameters.put("steals", newStats.steals());
        parameters.put("fouls", newStats.fouls());
        parameters.put("turnovers", newStats.turnovers());
        simpleJdbcInsert.execute(parameters);
        return newStats.toGameStats();
    }

    @Override
    public PlayerStats getByUserIdAndGameId(long userId, long gameId) {
        final String GET_BY_USER_ID_QUERY = "SELECT * FROM game_stats WHERE user_id = ? AND game_id = ?";
        List<GameStats> gameStats = jdbcTemplate.query(
            GET_BY_USER_ID_QUERY, 
            (rs, rowNum) -> GameStats.builder()
                .points(rs.getInt("points"))
                .assists(rs.getInt("assists"))
                .rebounds(rs.getInt("rebounds"))
                .blocks(rs.getInt("blocks"))
                .steals(rs.getInt("steals"))
                .turnovers(rs.getInt("turnovers"))
                .fouls(rs.getInt("fouls"))
                .build(),
            userId,
            gameId
        );
        User user = userRepository.findById(userId).orElseThrow();
        return new PlayerStats(user, gameStats);
    }

    @Override
    public GameStats updateByUserIdAndGameId(GameStatsDto updatedStats) {
        final String UPDATE_QUERY = "UPDATE game_stats SET points = ?, assists = ?, rebounds = ?, steals = ?, blocks = ?, turnovers = ?, fouls = ? WHERE user_id = ? AND game_id = ?";
        jdbcTemplate.update(
            UPDATE_QUERY, 
            updatedStats.points(), 
            updatedStats.assists(),
            updatedStats.rebounds(), 
            updatedStats.steals(), 
            updatedStats.blocks(), 
            updatedStats.turnovers(), 
            updatedStats.fouls(), 
            updatedStats.userId(), 
            updatedStats.gameId()
        );
       return updatedStats.toGameStats();
    }

    @Override
    public boolean deleteByUserIdAndGameId(long userId, long gameId) {
        final String DELETE_QUERY = "DELETE FROM game_stats where user_id = ? AND game_id = ?";
        int deleteCount = jdbcTemplate.update(DELETE_QUERY, userId, gameId);
        return deleteCount > 0;
    }
    
}
