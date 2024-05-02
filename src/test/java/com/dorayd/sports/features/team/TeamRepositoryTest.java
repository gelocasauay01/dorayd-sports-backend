package com.dorayd.sports.features.team;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import com.dorayd.sports.features.user.models.Gender;
import com.dorayd.sports.features.user.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.team.repositories.TeamRepository;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
public class TeamRepositoryTest {
    
    @Autowired
    private TeamRepository repository;

    @Test
    public void givenFindById_whenTeamExists_thenReturnSpecificTeam() {
        // Arrange
        Team expected = new Team(1L, "Team Rocket", new ArrayList<>());

        // Act
        Optional<Team> actual = repository.findById(1L);

        // Assert
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    public void givenFindById_whenTeamDoesNotExists_thenReturnEmpty() {
        // Act
        Optional<Team> actual = repository.findById(1000L);

        // Assert
        assertTrue(actual.isEmpty());
    }

    @Test 
    public void givenCreate_whenTeamIsValid_thenReturnCreatedTeam() {
        // Arrange
        Team input = new Team(null, "Digimon Gangsters", new ArrayList<>());

        // Act
        Team actual = repository.create(input);

        // Assert 
        assertNotNull(actual.getId());
        assertTrue(actual.getId().compareTo(0L) > 0);
        assertEquals(input, actual);
    }

    @Test
    public void givenUpdate_whenTeamExists_thenUpdateTeamAndReturn() {
        // Arrange
        final Long UPDATE_ID = 2L;
        Team update = new Team(null, "Tekken Gang", new ArrayList<>());

        // Act
        Team actual = repository.update(UPDATE_ID, update);
        Optional<Team> queriedAActual = repository.findById(actual.getId());

        // Assert
        assertEquals(UPDATE_ID, actual.getId());
        assertEquals(update, actual);
        assertTrue(queriedAActual.isPresent());
        assertEquals(UPDATE_ID, queriedAActual.get().getId());
        assertEquals(update, queriedAActual.get());
    }

    @Test
    public void givenDelete_whenTeamExists_thenDelete() {
        // Arrange
        final long DELETE_ID = 4L;

        // Act 
        boolean isDeleted = repository.delete(DELETE_ID);
        Optional<Team> queried = repository.findById(DELETE_ID);

        // Assert
        assertTrue(isDeleted);
        assertFalse(queried.isPresent());
    }

    @Test
    public void givenAddPlayer_whenTeamAndUserExists_thenAddPlayerAndReturnTeam() {
        // Arrange
        User expectedAddedPlayer = new User(1L, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        Team expectedTeam = new Team(1L, "Team Rocket", List.of(expectedAddedPlayer));

        // Act
        Team actual = repository.addPlayer(expectedAddedPlayer.getId(), expectedTeam.getId());

        // Assert
        assertTrue(actual.getPlayers().contains(expectedAddedPlayer));
    }

    @Test
    public void givenAddPlayer_whenUserDoesNotExist_thenThrowDataIntegrityViolationException() {
        // Arrange
        User expectedAddedPlayer = new User(132423423423L, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        Team expectedTeam = new Team(1L, "Team Rocket", List.of(expectedAddedPlayer));

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> repository.addPlayer(expectedAddedPlayer.getId(), expectedTeam.getId()));
    }

   
}
