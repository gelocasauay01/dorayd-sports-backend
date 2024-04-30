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
        final Team expected = new Team(1L, "Team Rocket", new ArrayList<>());

        // Act
        final Optional<Team> actual = repository.findById(1L);

        // Assert
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    public void givenFindById_whenTeamDoesNotExists_thenReturnEmpty() {
        // Act
        final Optional<Team> actual = repository.findById(1000L);

        // Assert
        assertTrue(actual.isEmpty());
    }

    @Test 
    public void givenCreate_whenTeamIsValid_thenReturnCreatedTeam() {
        // Arrange
        final Team input = new Team(null, "Digimon Gangsters", new ArrayList<>());

        // Act
        final Team actual = repository.create(input);

        // Assert 
        assertNotNull(actual.getId());
        assertTrue(actual.getId().compareTo(0L) > 0);
        assertEquals(input, actual);
    }

    @Test
    public void givenUpdate_whenTeamExists_thenUpdateTeamAndReturn() {
        // Arrange
        final Team update = new Team(null, "Tekken Gang", new ArrayList<>());
        final Long id = 2L;

        // Act
        final Team actual = repository.update(id, update);
        final Optional<Team> queriedAActual = repository.findById(actual.getId());

        // Assert
        assertEquals(id, actual.getId());
        assertEquals(update, actual);
        assertTrue(queriedAActual.isPresent());
        assertEquals(id, queriedAActual.get().getId());
        assertEquals(update, queriedAActual.get());
    }

    @Test
    public void givenDelete_whenTeamExists_thenDelete() {
        // Arrange
        final long deleteId = 4L;

        // Act 
        final boolean isDeleted = repository.delete(deleteId);
        final Optional<Team> queried = repository.findById(deleteId);

        // Assert
        assertTrue(isDeleted);
        assertFalse(queried.isPresent());
    }

    @Test
    public void givenAddPlayer_whenTeamAndUserExists_thenAddPlayerAndReturnTeam() {
        // Arrange
        final User expectedAddedPlayer = new User(1L, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        final Team expectedTeam = new Team(1L, "Team Rocket", List.of(expectedAddedPlayer));

        // Act
        final Team actual = repository.addPlayer(expectedAddedPlayer.getId(), expectedTeam.getId());

        // Assert
        assertTrue(actual.getPlayers().contains(expectedAddedPlayer));
    }

    @Test
    public void givenAddPlayer_whenUserDoesNotExist_thenThrowDataIntegrityViolationException() {
        // Arrange
        final User expectedAddedPlayer = new User(132423423423L, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        final Team expectedTeam = new Team(1L, "Team Rocket", List.of(expectedAddedPlayer));

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> repository.addPlayer(expectedAddedPlayer.getId(), expectedTeam.getId()));
    }

   
}
