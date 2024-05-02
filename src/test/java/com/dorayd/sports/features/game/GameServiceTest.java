package com.dorayd.sports.features.game;

import com.dorayd.sports.features.game.dto.GameDto;
import com.dorayd.sports.features.game.models.Game;
import com.dorayd.sports.features.game.services.GameService;
import com.dorayd.sports.features.league.models.League;
import com.dorayd.sports.features.team.models.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class GameServiceTest {

    @Autowired
    private GameService service;

    @Test
    public void givenFindById_whenGameExists_thenReturnGame() {
        // Arrange
        final Long FIND_ID = 7L;
        Game expected = new Game(
            FIND_ID,
            new League(5L, "Creators league", new ArrayList<>()),
            new Team(5L, "EG", new ArrayList<>()),
            new Team(3L, "My Chemical Romance", new ArrayList<>()),
            LocalDateTime.of(1985, 1, 11, 0, 0)
        );

        // Act
        Optional<Game> actual = service.findById(FIND_ID);

        // Assert
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    public void givenFindById_whenGameDoesNotExist_thenReturnEmptyGame() {
        // Arrange
        final Long GAME_ID = 123123L;

        // Act
        Optional<Game> actual = service.findById(GAME_ID);

        // Assert
        assertFalse(actual.isPresent());
    }

    @Test
    public void givenCreate_whenLeagueAndTeamsExist_thenSaveAndReturnGame() {
        // Arrange
        LocalDateTime expectedSchedule = LocalDateTime.of(1985, 1, 11, 0, 0);
        Game expected = new Game(
        null,
            new League(1L, "Greenpark league", new ArrayList<>()),
            new Team(1L, "Team Rocket", new ArrayList<>()),
            new Team(4L, "Team Magma", new ArrayList<>()),
            expectedSchedule
        );
        GameDto input = new GameDto(null, 1L, 1L, 4L, expectedSchedule);

        // Act
        Game actual = service.create(input);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void givenCreateGame_whenTeamDoesNotExist_thenThrowDataIntegrityException() {
        // Arrange
        GameDto input = new GameDto(null, 2L, 104534L, 45645L, LocalDateTime.of(2024, 5, 2, 0, 0));

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> service.create(input));
    }

    @Test
    public void givenCreateGame_whenLeagueDoesNotExist_thenThrowDataIntegrityException() {
        // Arrange
        GameDto input = new GameDto(null, 4534543534L, 1L, 2L, LocalDateTime.of(2024, 5, 2, 0, 0));

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> service.create(input));
    }

    @Test
    public void givenUpdateSchedule_whenScheduleIsValid_thenUpdateScheduleAndReturnGame() {
        // Arrange
        LocalDateTime expectedSchedule = LocalDateTime.of(2024, 9, 2, 0, 0);
        Game expected = new Game(
            4L,
            new League(4L, "Vista league", new ArrayList<>()),
            new Team(1L, "Team Rocket", new ArrayList<>()),
            new Team(4L, "Team Magma", new ArrayList<>()),
            expectedSchedule
        );

        // Act
        Game actual = service.updateSchedule(4L, expectedSchedule);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void givenUpdateSchedule_whenGameDoesNotExist_thenThrowNoSuchElementException() {
        // Arrange
        LocalDateTime expectedSchedule = LocalDateTime.of(2024, 9, 2, 0, 0);

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> service.updateSchedule(3242344L, expectedSchedule));
    }
}
