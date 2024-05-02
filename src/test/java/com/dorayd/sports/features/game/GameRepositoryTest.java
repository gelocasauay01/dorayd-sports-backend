package com.dorayd.sports.features.game;

import com.dorayd.sports.features.game.dto.GameDto;
import com.dorayd.sports.features.game.repositories.GameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class GameRepositoryTest {

    @Autowired
    private GameRepository repository;

    @Test
    public void givenFindById_whenGameExists_thenReturnGameDto() {
        // Arrange
        final Long FIND_ID = 2L;
        GameDto expected = new GameDto(FIND_ID, 2L, 6L,  3L, LocalDateTime.of(1985, 1, 11, 0, 0));

        // Act
        Optional<GameDto> actual = repository.findById(FIND_ID);

        // Assert
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    public void givenFindById_whenGameDoesNotExist_thenReturnEmptyGame() {
        // Arrange
        final Long GAME_ID = 123123L;

        // Act
        Optional<GameDto> actual = repository.findById(GAME_ID);

        // Assert
        assertFalse(actual.isPresent());
    }

    @Test
    public void givenCreateGame_whenTeamsAndLeagueExist_thenCreateAndReturnGameDto() {
        // Arrange
        GameDto expected = new GameDto(null, 2L, 1L, 2L, LocalDateTime.of(2024, 5, 2, 0, 0));

        // Act
        GameDto actual = repository.create(expected);
        expected = expected.withId(actual.id());

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void givenCreateGame_whenTeamDoesNotExist_thenThrowDataIntegrityException() {
        // Arrange
        GameDto input = new GameDto(null, 2L, 104534L, 45645L, LocalDateTime.of(2024, 5, 2, 0, 0));

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> repository.create(input));
    }

    @Test
    public void givenCreateGame_whenLeagueDoesNotExist_thenThrowDataIntegrityException() {
        // Arrange
        GameDto input = new GameDto(null, 4534543534L, 1L, 2L, LocalDateTime.of(2024, 5, 2, 0, 0));

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> repository.create(input));
    }

    @Test
    public void givenUpdateSchedule_whenScheduleIsValid_thenUpdateScheduleAndReturnGame() {
        // Arrange
        LocalDateTime expectedSchedule = LocalDateTime.of(2024, 9, 2, 0, 0);
        GameDto expected = new GameDto(1L, 1L, 1L,  2L, expectedSchedule);

        // Act
        GameDto actual = repository.updateSchedule(1L, expectedSchedule);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void givenUpdateSchedule_whenGameDoesNotExist_thenThrowNoSuchElementException() {
        // Arrange
        LocalDateTime expectedSchedule = LocalDateTime.of(2024, 9, 2, 0, 0);

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> repository.updateSchedule(123423L, expectedSchedule));
    }

    @Test
    public void givenDelete_whenGameDoesExists_thenDeleteAndReturnTrue() {
        // Arrange
        final Long GAME_ID = 3L;

        // Act
        boolean isDeleted = repository.delete(GAME_ID);
        Optional<GameDto> queriedGame = repository.findById(GAME_ID);

        // Assert
        assertTrue(isDeleted);
        assertFalse(queriedGame.isPresent());
    }

    @Test
    public void givenDelete_whenGameDoesNotExists_thenReturnFalse() {
        // Arrange
        final Long GAME_ID = 3345345L;

        // Act
        boolean isDeleted = repository.delete(GAME_ID);

        // Assert
        assertFalse(isDeleted);
    }
}
