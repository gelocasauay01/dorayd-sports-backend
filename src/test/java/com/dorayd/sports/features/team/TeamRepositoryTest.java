package com.dorayd.sports.features.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.dorayd.sports.features.team.models.Team;
import com.dorayd.sports.features.team.repositories.TeamRepository;


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
        assertEquals(expected.getId(), actual.get().getId());
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
        assertEquals(input.getName(), actual.getName());
        assertTrue(input.getPlayers().isEmpty());
    }

    @Test
    public void givenUpdate_whenTeamExists_thenUpdateTeamAndReturn() {
        // Arrange
        Team update = new Team(null, "Tekken Gang", new ArrayList<>());
        Long id = 2L;

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
        final long DELETE_ID = 4L;

        // Act 
        boolean isDeleted = repository.delete(DELETE_ID);
        Optional<Team> queried = repository.findById(DELETE_ID);

        // Assert
        assertTrue(isDeleted);
        assertFalse(queried.isPresent());
    }

   
}
