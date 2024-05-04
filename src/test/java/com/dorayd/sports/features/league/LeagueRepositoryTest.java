package com.dorayd.sports.features.league;

import com.dorayd.sports.features.team.models.Team;

import io.jsonwebtoken.lang.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.dorayd.sports.features.league.dto.LeagueDto;
import com.dorayd.sports.features.league.models.League;
import com.dorayd.sports.features.league.repositories.LeagueRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class LeagueRepositoryTest {

    private static final Long VALID_LEAGUE_ID = 5L;
    
    @Autowired
    private LeagueRepository repository;

    @Test
    public void givenFindById_whenLeagueExists_thenReturnSpecificLeague() {
        // Arrange
        final long FIND_ID = 1l;
        League expected = new League(FIND_ID, "Greenpark league", Collections.emptyList());

        // Act
        Optional<League> actual = repository.findById(FIND_ID);

        // Assert
        assertTrue(actual.isPresent());
        assertEquals(expected.getId(), actual.get().getId());
        assertEquals(expected, actual.get());
    }

    @Test
    public void givenFindById_whenLeagueDoesExists_thenReturnEmpty() {
        // Act
        long INVALID_FIND_ID = 10000l;
        Optional<League> actual = repository.findById(INVALID_FIND_ID);

        // Assert
        assertTrue(actual.isEmpty());
    }

    @Test 
    public void givenCreate_whenLeagueIsValid_thenReturnCreatedLeague() {
        // Arrange
        LeagueDto input = new LeagueDto("Digimon Battles", Collections.emptyList());
        League expected = new League(0, "Digimon Battles", Collections.emptyList());

        // Act
        League actual = repository.create(input);

        // Assert 
        assertEquals(expected, actual);
    }

    @Test
    public void givenUpdate_whenLeagueExists_thenUpdateLeagueAndReturn() {
        // Arrange
        final long UPDATE_ID = 2l;
        LeagueDto input = new LeagueDto("Smash Tournament", Collections.emptyList());
        League expected = new League(0, "Smash Tournament", Collections.emptyList());
        

        // Act
        League actual = repository.update(UPDATE_ID, input);
        Optional<League> queriedAActual = repository.findById(actual.getId());

        // Assert
        assertEquals(expected, actual);
        assertEquals(expected, queriedAActual.get());
    }

    @Test
    public void givenDelete_whenLeagueExists_thenDelete() {
        // Arrange
        final long DELETE_ID = 4L;

        // Act 
        boolean isDeleted = repository.delete(DELETE_ID);
        Optional<League> queried = repository.findById(DELETE_ID);

        // Assert
        assertTrue(isDeleted);
        assertFalse(queried.isPresent());
    }

    @Test
    public void givenAddTeam_whenTeamExists_thenAddTeamAndReturnLeague() {
        // Arrange
        Team expectedTeam = new Team(1l, "Team Rocket", Collections.emptyList());

        // Act
        League actual = repository.addTeam(expectedTeam.getId(), VALID_LEAGUE_ID);

        // Assert
        assertEquals(VALID_LEAGUE_ID, actual.getId());
        assertTrue(actual.getTeams().contains(expectedTeam));
    }

    @Test
    public void givenAddTeam_whenTeamDoesNotExist_thenThrowDataIntegrityException() {
        // Arrange
        Team expectedTeam = new Team(100000l, "Team Rocket", Collections.emptyList());

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> repository.addTeam(expectedTeam.getId(), VALID_LEAGUE_ID));
    }

    @Test
    public void givenAddTeam_whenLeagueDoesNotExist_thenThrowDataIntegrityException() {
        // Arrange
        Team expectedTeam = new Team(100000l, "Team Rocket", Collections.emptyList());
        long leagueId = 13453434l;

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> repository.addTeam(expectedTeam.getId(), leagueId));
    }

}
