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

import com.dorayd.sports.features.team.dto.TeamDto;
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
        final long FIND_ID = 1l; 
        Team expected = new Team(FIND_ID, "Team Rocket", new ArrayList<>());

        // Act
        Optional<Team> actual = repository.findById(FIND_ID);

        // Assert
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    public void givenFindById_whenTeamDoesNotExists_thenReturnEmpty() {
        // Arrange
        final long INVALID_ID = 1000l;

        // Act
        Optional<Team> actual = repository.findById(INVALID_ID);

        // Assert
        assertTrue(actual.isEmpty());
    }

    @Test 
    public void givenCreate_whenTeamIsValid_thenReturnCreatedTeam() {
        // Arrange
        final String CREATE_NAME = "Digimon Gangsters";
        TeamDto input = new TeamDto(CREATE_NAME, new ArrayList<>());
        Team expected = new Team(0l, CREATE_NAME, new ArrayList<>());

        // Act
        Team actual = repository.create(input);

        // Assert 
        assertEquals(expected, actual);
    }

    @Test
    public void givenUpdate_whenTeamExists_thenUpdateTeamAndReturn() {
        // Arrange
        final long UPDATE_ID = 2l;
        final String UPDATE_NAME = "Tekken Gang";
        TeamDto input = new TeamDto(UPDATE_NAME, new ArrayList<>());
        Team expected = new Team(0l, UPDATE_NAME, new ArrayList<>());

        // Act
        Team actual = repository.update(UPDATE_ID, input);

        // Assert
        assertEquals(expected, actual);
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
    public void givenAddPlayer_whenTeamAndUserExists_thenAddPlayerAndReturnTeam() {
        // Arrange
        User expectedAddedPlayer = new User(1l, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        Team expectedTeam = new Team(1l, "Team Rocket", List.of(expectedAddedPlayer));

        // Act
        Team actual = repository.addPlayer(expectedAddedPlayer.getId(), expectedTeam.getId());

        // Assert
        assertTrue(actual.getPlayers().contains(expectedAddedPlayer));
    }

    @Test
    public void givenAddPlayer_whenUserDoesNotExist_thenThrowDataIntegrityViolationException() {
        // Arrange
        User expectedAddedPlayer = new User(132423423423l, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        Team expectedTeam = new Team(1L, "Team Rocket", List.of(expectedAddedPlayer));

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () -> repository.addPlayer(expectedAddedPlayer.getId(), expectedTeam.getId()));
    }

   
}
