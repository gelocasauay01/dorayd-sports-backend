package com.dorayd.sports.features.league;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import com.dorayd.sports.features.league.models.League;
import com.dorayd.sports.features.league.services.LeagueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.dorayd.sports.features.team.models.Team;

@SpringBootTest
@ActiveProfiles("test")
public class LeagueServiceTest {

    @Autowired
    private LeagueService service;

    @Test
    public void givenAddTeam_whenTeamExists_thenAddTeamAndReturnLeague() {
        // Arrange
        final Team expectedTeam = new Team(1L, "Team Rocket", new ArrayList<>());
        final Long leagueId = 1L;

        // Act
        final League actual = service.addTeam(expectedTeam, leagueId);

        // Assert
        assertEquals(leagueId, actual.getId());
        assertTrue(actual.getTeams().contains(expectedTeam));
    }

    @Test
    public void givenAddTeam_whenTeamDoesNotExist_thenCreateTeamAndSaveMembership() {
        // Arrange
        final Team expectedTeam = new Team(null, "Team Harina", new ArrayList<>());
        final Long leagueId = 1L;

        // Act
        final League actual = service.addTeam(expectedTeam, leagueId);

        // Assert
        assertTrue(actual.getTeams().contains(expectedTeam));
    }
}
