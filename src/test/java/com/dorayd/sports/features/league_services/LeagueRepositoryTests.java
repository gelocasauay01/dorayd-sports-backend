package com.dorayd.sports.features.league_services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.dorayd.sports.features.league.models.League;
import com.dorayd.sports.features.league.repositories.LeagueRepository;
import com.dorayd.sports.helpers.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class LeagueRepositoryTests {
    
    @Autowired
    private LeagueRepository repository;

    @Autowired 
    private JdbcTemplate jdbcTemplate;


    @Test
    public void givenFindById_whenLeagueExists_thenReturnSpecificLeague() {
        // Arrange
        League expected = new League(1l, "Greenpark league");

        // Act
        Optional<League> actual = repository.findById(1l);

        // Assert
        assertTrue(actual.isPresent());
        assertEquals(expected.getId(), actual.get().getId());
        assertEquals(expected.getTitle(), actual.get().getTitle());
    }

    @Test
    public void givenFindById_whenLeagueDoesExists_thenReturnNotFoundStatus() {
        // Act
        Optional<League> actual = repository.findById(10l);

        // Assert
        assertTrue(!actual.isPresent());
    }

    @Test 
    public void givenCreate_whenLeagueIsValid_thenReturnCreatedLeague() {
        // Arrange
        League input = new League(null, "Digimon Battles");

        // Act
        League actual = repository.create(input);

        // Assert 
        assertNotNull(actual.getId());
        assertTrue(actual.getId().compareTo(0l) > 0);
        assertEquals(input.getTitle(), actual.getTitle());
    }

    @Test
    public void givenUpdate_whenLeagueExists_thenUpdateLeagueAndReturn() {
        // Arrange
        League update = new League(null, "Tekken Tournament");
        Long id = 2l;

        // Act
        League actual = repository.update(id, update);
        Optional<League> queriedAActual = repository.findById(actual.getId());

        // Assert
        assertEquals(id, actual.getId());
        assertEquals(update.getTitle(), actual.getTitle());
        assertTrue(queriedAActual.isPresent());
        assertEquals(id, queriedAActual.get().getId());
        assertEquals(update.getTitle(), queriedAActual.get().getTitle());
    }

    @Test
    public void givenDelete_whenLeagueExists_thenDelete() {
        // Arrange
        Long id = SqlQueryExecutor.insert("INSERT INTO league(title) VALUES('Sitio Kapayapaan League')", jdbcTemplate);

        // Act 
        boolean isDeleted = repository.delete(id);
        Optional<League> queried = repository.findById(id);

        // Assert
        assertTrue(isDeleted);
        assertFalse(queried.isPresent());
    }

}
