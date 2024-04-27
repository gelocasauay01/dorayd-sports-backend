package com.dorayd.sports.features.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.team.repositories.TeamRepository;
import com.dorayd.sports.features.user.models.Gender;
import com.dorayd.sports.features.user.models.User;


@SpringBootTest
@ActiveProfiles("test")
public class TeamRepositoryTest {
    
    @Autowired
    private TeamRepository repository;

    @Test
    public void givenFindById_whenTeamExists_thenReturnSpecificTeam() {
        // Arrange
        Team expected = new Team(1l, "Team Rocket", new ArrayList<>());

        // Act
        Optional<Team> actual = repository.findById(1l);

        // Assert
        assertTrue(actual.isPresent());
        assertEquals(expected.getId(), actual.get().getId());
    }

    @Test
    public void givenFindById_whenTeamDoesExists_thenReturnNotFoundStatus() {
        // Act
        Optional<Team> actual = repository.findById(10l);

        // Assert
        assertTrue(!actual.isPresent());
    }

    @Test 
    public void givenCreate_whenTeamIsValid_thenReturnCreatedTeam() {
        // Arrange
        Team input = new Team(null, "Digimon Gangsters", new ArrayList<>());

        // Act
        Team actual = repository.create(input);

        // Assert 
        assertNotNull(actual.getId());
        assertTrue(actual.getId().compareTo(0l) > 0);
        assertEquals(input.getName(), actual.getName());
        assertTrue(input.getPlayers().isEmpty());
    }

    @Test
    public void givenUpdate_whenTeamExists_thenUpdateTeamAndReturn() {
        // Arrange
        Team update = new Team(null, "Tekken Gang", new ArrayList<>());
        Long id = 2l;

        // Act
        Team actual = repository.update(id, update);
        Optional<Team> queriedAActual = repository.findById(actual.getId());

        // Assert
        assertEquals(id, actual.getId());
        assertEquals(update.getName(), actual.getName());
        assertTrue(queriedAActual.isPresent());
        assertEquals(id, queriedAActual.get().getId());
        assertEquals(update.getName(), queriedAActual.get().getName());
    }

    @Test
    public void givenDelete_whenTeamExists_thenDelete() {
        // Arrange
        final long DELETE_ID = 4l;

        // Act 
        boolean isDeleted = repository.delete(DELETE_ID);
        Optional<Team> queried = repository.findById(DELETE_ID);

        // Assert
        assertTrue(isDeleted);
        assertFalse(queried.isPresent());
    }

    @Test
    public void givenAddPlayer_whenUserAndTeamExists_thenAddPlayerToTeamAndReturnTeam() {
        // Arrange
        User expectedUser = new User(1l, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        Long teamId = 1l;

        // Act
        Team actual = repository.addPlayer(expectedUser, teamId);

        // Assert
        assertEquals(teamId, actual.getId());
        assertTrue(TeamTestHelper.isUserInTeam(expectedUser, actual));
    }

    @Test
    public void givenAddPlayer_whenUserDoesNotExist_thenSaveUserAndAddPlayerToTeamThenReturnTeam() {
        // Arrange
        User expectedUser = new User(null, "Josdvsdvdseph", "Masdfsdvo", "Cassdvsdvauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        Long teamId = 1l;

        // Act
        Team actual = repository.addPlayer(expectedUser, teamId);

        // Assert
        assertEquals(teamId, actual.getId());
        assertTrue(TeamTestHelper.isUserInTeam(expectedUser, actual));
    }

    @Test
    public void givenAddPlayer_whenTeamDoesNotExists_thenThrowDataIntegrityViolationException() {
        // Arrange
        User expectedUser = new User(100l, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        Long teamId = 1000l;

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> repository.addPlayer(expectedUser, teamId));
    }

    @Test
    public void givenCreate_whenTeamHasPlayers_thenSaveTeamAndPlayers() {
        // Arrange
        List<User> expectedPlayers = new ArrayList<>();
        expectedPlayers.add(new User(1l, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE));
        expectedPlayers.add(new User(null, "dsvsdvsdv", "dfbdfbfdb", "fgngfngf", LocalDate.of(1999, 8, 1), Gender.FEMALE));
        expectedPlayers.add(new User(null, "Joseph", "Mardghmghmhgmhgo", "fgngfn", LocalDate.of(1999, 8, 1), Gender.NON_BINARY));
        Team expected = new Team(1l, "Team Rocket", expectedPlayers);

        // Act
        Team actual = repository.create(expected);

        // Assert
        assertTrue(TeamTestHelper.isTeamEqual(expected, actual));
    }
}
