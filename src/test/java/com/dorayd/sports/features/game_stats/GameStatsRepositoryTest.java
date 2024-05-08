package com.dorayd.sports.features.game_stats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import com.dorayd.sports.features.game_stats.dto.GameStatsDto;
import com.dorayd.sports.features.game_stats.models.GameStats;
import com.dorayd.sports.features.game_stats.models.PlayerStats;
import com.dorayd.sports.features.game_stats.repositories.GameStatsRepository;
import com.dorayd.sports.features.user.models.Gender;
import com.dorayd.sports.features.user.models.User;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class GameStatsRepositoryTest {
    
    @Autowired
    private GameStatsRepository repository;

    @Test
    public void givenCreate_whenGamesStatsAreValid_thenCreateAndReturnGameStats() {
        // Arrange 
        GameStatsDto newStats = new GameStatsDto(2, 1, 10, 5, 0, 3, 1, 4, 2);
        GameStats expected = new GameStats(10, 5, 0, 3, 1, 4, 2);

        // Act
        GameStats actual = repository.create(newStats);
        
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void givenGetByPlayerId_whenPlayerExistAndHasStats_thenReturnPlayerStats() {
        // Arrange
        final long USER_ID = 1L;
        final long GAME_ID = 1L;
        User user = new User(USER_ID, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        List<GameStats> gameStats = List.of(
            new GameStats(10, 2, 2, 0, 0, 0, 0)
        );
        PlayerStats expected = new PlayerStats(user, gameStats);

        // Act
        final PlayerStats actual = repository.getByUserIdAndGameId(USER_ID, GAME_ID);

        // Arrange
        assertEquals(expected, actual);
    }

    @Test
    public void givenUpdateStatsByUserIdAndGameId_whenGameStatsExist_thenUpdateStatsAndReturn() {
        // Arrange
        GameStatsDto updatedStats = new GameStatsDto(2, 2, 33, 2, 2, 0, 0, 0, 0);
        GameStats expected = new GameStats(33, 2, 2, 0, 0, 0, 0);
        
        // Act
        GameStats actual = repository.updateByUserIdAndGameId(updatedStats);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void givenDeleteByUserIdAndGameId_whenGameStatsExist_thenDeleteAndReturnTrue() {
        // Arrange
        final long USER_ID = 2L;
        final long GAME_ID = 4L;

        // Act
        boolean isDeleted = repository.deleteByUserIdAndGameId(USER_ID, GAME_ID);

        // Assert
        assertTrue(isDeleted);
    }
}
