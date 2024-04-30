package com.dorayd.sports.features.league;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.dorayd.sports.features.league.models.League;
import com.dorayd.sports.features.league.repositories.LeagueRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class LeagueRepositoryTest {
    
    @Autowired
    private LeagueRepository repository;

    @Test
    public void givenFindById_whenLeagueExists_thenReturnSpecificLeague() {
        // Arrange
        League expected = new League(1L, "Greenpark league");

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
        League input = new League(null, "Digimon Battles");

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
        League update = new League(null, "Tekken Tournament");
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

}
