package com.dorayd.sports.features.league;

import com.dorayd.sports.features.team.models.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.dorayd.sports.features.league.models.League;
import com.dorayd.sports.features.league.repositories.LeagueRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class LeagueRepositoryTest {

    private static final Long ADD_TEAM_ID = 5L;
    
    @Autowired
    private LeagueRepository repository;

    @Test
    public void givenFindById_whenLeagueExists_thenReturnSpecificLeague() {
        // Arrange
        League expected = new League(1L, "Greenpark league", new ArrayList<>());

        // Act
        Optional<League> actual = repository.findById(1L);

        // Assert
        assertTrue(actual.isPresent());
        assertEquals(expected.getId(), actual.get().getId());
        assertEquals(expected, actual.get());
    }

    @Test
    public void givenFindById_whenLeagueDoesExists_thenReturnEmpty() {
        // Act
        Optional<League> actual = repository.findById(10000L);

        // Assert
        assertTrue(actual.isEmpty());
    }

    @Test 
    public void givenCreate_whenLeagueIsValid_thenReturnCreatedLeague() {
        // Arrange
        League input = new League(null, "Digimon Battles", new ArrayList<>());

        // Act
        League actual = repository.create(input);

        // Assert 
        assertNotNull(actual.getId());
        assertTrue(actual.getId().compareTo(0L) > 0);
        assertEquals(input, actual);
    }

    @Test
    public void givenUpdate_whenLeagueExists_thenUpdateLeagueAndReturn() {
        // Arrange
        League update = new League(null, "Tekken Tournament", new ArrayList<>());
        Long updateId = 2L;

        // Act
        League actual = repository.update(updateId, update);
        Optional<League> queriedAActual = repository.findById(actual.getId());

        // Assert
        assertEquals(updateId, actual.getId());
        assertEquals(update, actual);
        assertTrue(queriedAActual.isPresent());
        assertEquals(updateId, queriedAActual.get().getId());
        assertEquals(update, queriedAActual.get());
    }

    @Test
    public void givenDelete_whenLeagueExists_thenDelete() {
        // Arrange
        final long deleteId = 4L;

        // Act 
        boolean isDeleted = repository.delete(deleteId);
        Optional<League> queried = repository.findById(deleteId);

        // Assert
        assertTrue(isDeleted);
        assertFalse(queried.isPresent());
    }

    @Test
    public void givenAddTeam_whenTeamExists_thenAddTeamAndReturnLeague() {
        // Arrange
        Team expectedTeam = new Team(1L, "Team Rocket", new ArrayList<>());

        // Act
        League actual = repository.addTeam(expectedTeam.getId(), ADD_TEAM_ID);

        // Assert
        assertEquals(ADD_TEAM_ID, actual.getId());
        assertTrue(actual.getTeams().contains(expectedTeam));
    }

    @Test
    public void givenAddTeam_whenTeamDoesNotExist_thenThrowDataIntegrityException() {
        // Arrange
        Team expectedTeam = new Team(100000L, "Team Rocket", new ArrayList<>());

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> repository.addTeam(expectedTeam.getId(), ADD_TEAM_ID));
    }

    @Test
    public void givenAddTeam_whenLeagueDoesNotExist_thenThrowDataIntegrityException() {
        // Arrange
        Team expectedTeam = new Team(100000L, "Team Rocket", new ArrayList<>());
        Long leagueId = 13453434L;

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> repository.addTeam(expectedTeam.getId(), leagueId));
    }

}
