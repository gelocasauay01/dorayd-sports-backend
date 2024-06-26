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

import com.dorayd.sports.features.team.dto.TeamDto;
import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.team.services.TeamService;
import com.dorayd.sports.features.user.models.Gender;
import com.dorayd.sports.features.user.models.User;

@SpringBootTest
@ActiveProfiles("test")
public class TeamServiceTest {

    private static final long VALID_TEAM_ID = 1L;
    private static final long VALID_USER_ID = 1L;

    @Autowired
    private TeamService service;

     @Test
    public void givenAddPlayer_whenUserAndTeamExists_thenAddPlayerToTeamAndReturnTeam() {
        // Arrange
        final long VALID_ADD_PLAYER_ID = 8L;
        User expectedUser = new User(VALID_ADD_PLAYER_ID, "Hadfgyley", "Mdfgark", "Jones", LocalDate.of(1985, 1, 11), Gender.FEMALE);

        // Act
        final Team actual = service.addPlayer(VALID_ADD_PLAYER_ID, VALID_TEAM_ID);

        // Assert
        assertTrue(actual.getPlayers().contains(expectedUser));
    }

    @Test
    public void givenAddPlayer_whenUserDoesNotExist_thenThrowDataIntegrityException() {
        // Arrange
        final long NOT_EXISTING_USER_ID = 123123L;

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> service.addPlayer(NOT_EXISTING_USER_ID, VALID_TEAM_ID));
    }

    @Test
    public void givenAddPlayer_whenTeamDoesNotExists_thenThrowDataIntegrityViolationException() {
        // Arrange
        final long INVALID_TEAM_ID = 1000L;

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> service.addPlayer(VALID_USER_ID, INVALID_TEAM_ID));
    }

    @Test
    public void givenCreate_whenTeamHasPlayers_thenSaveTeamAndPlayers() {
        // Arrange
        
        List<User> expectedPlayers = new ArrayList<>();
        expectedPlayers.add(new User(VALID_USER_ID, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE));
        Team expected = new Team(VALID_TEAM_ID, "Team Rocket", expectedPlayers);
        TeamDto input = new TeamDto("Team Rocket", List.of(1L));

        // Act
        final Team actual = service.create(input);

        // Assert
        assertEquals(expected, actual);
    }
}
