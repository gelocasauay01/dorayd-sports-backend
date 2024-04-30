package com.dorayd.sports.features.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.team.services.TeamService;
import com.dorayd.sports.features.user.models.Gender;
import com.dorayd.sports.features.user.models.User;

@SpringBootTest
@ActiveProfiles("test")
public class TeamServiceTest {
    
    @Autowired
    private TeamService service;

     @Test
    public void givenAddPlayer_whenUserAndTeamExists_thenAddPlayerToTeamAndReturnTeam() {
        // Arrange
        User expectedUser = new User(1L, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        Long teamId = 1L;

        // Act
        Team actual = service.addPlayer(expectedUser, teamId);

        // Assert
        assertEquals(teamId, actual.getId());
        assertTrue(actual.getPlayers().contains(expectedUser));
    }

    @Test
    public void givenAddPlayer_whenUserDoesNotExist_thenSaveUserAndAddPlayerToTeamThenReturnTeam() {
        // Arrange
        User expectedUser = new User(null, "Josdvsdvdseph", "Masdfsdvo", "Cassdvsdvauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        Long teamId = 1L;

        // Act
        Team actual = service.addPlayer(expectedUser, teamId);

        // Assert
        assertEquals(teamId, actual.getId());
        assertTrue(actual.getPlayers().contains(expectedUser));
    }

    @Test
    public void givenAddPlayer_whenTeamDoesNotExists_thenThrowDataIntegrityViolationException() {
        // Arrange
        User expectedUser = new User(100L, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        Long teamId = 1000L;

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> service.addPlayer(expectedUser, teamId));
    }

    @Test
    public void givenCreate_whenTeamHasPlayers_thenSaveTeamAndPlayers() {
        // Arrange
        List<User> expectedPlayers = new ArrayList<>();
        expectedPlayers.add(new User(1L, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE));
        expectedPlayers.add(new User(null, "dsvsdvsdv", "dfbdfbfdb", "fgngfngf", LocalDate.of(1999, 8, 1), Gender.FEMALE));
        expectedPlayers.add(new User(null, "Joseph", "Mardghmghmhgmhgo", "fgngfn", LocalDate.of(1999, 8, 1), Gender.NON_BINARY));
        Team expected = new Team(1L, "Team Rocket", expectedPlayers);

        // Act
        Team actual = service.create(expected);

        // Assert
        assertEquals(expected, actual);
    }
}
