package com.dorayd.sports.features.game;

import com.dorayd.sports.core.test_templates.IntegrationTestWithAuthentication;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class GameControllerTest extends IntegrationTestWithAuthentication {

    @Test
    public void givenFindById_whenGameExists_thenReturnSpecificGame() throws Exception {
        // Arrange
        final int FIND_ID = 1;

        // Act
        MvcResult result = mockMvc
            .perform(get("/api/game/{id}", FIND_ID).with(user(userDetails)))
            .andReturn();
        String content = result.getResponse().getContentAsString();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertTrue(content.contains("Greenpark league"));
        assertTrue(content.contains("Team Rocket"));
        assertTrue(content.contains("OG"));
    }

    @Test
    public void givenCreate_whenLeagueAndTeamsExist_thenSaveAndReturnGame() throws Exception {
        // Arrange
        String requestBody = """
            {
                "leagueId": 2,
                "teamAId": 3,
                "teamBId": 4,
                "schedule": "2016-08-18T14:27"
            }
        """;

        // Act
        MvcResult result = mockMvc
            .perform(post("/api/game")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andReturn();
        String content = result.getResponse().getContentAsString();

        // Assert
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertTrue(content.contains("Pokemon league"));
        assertTrue(content.contains("My Chemical Romance"));
        assertTrue(content.contains("Team Magma"));
    }

    @Test
    public void givenUpdateSchedule_whenGameExist_thenUpdateScheduleAndReturnGame() throws Exception {
        // Arrange
        String dateInput = "2016-08-18T14:27";

        // Act
        MvcResult result = mockMvc
            .perform(patch("/api/game/{id}/update_schedule", 5)
                .with(user(userDetails))
                .param("schedule", dateInput))
            .andReturn();
        String content = result.getResponse().getContentAsString();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertTrue(content.contains("Vista league"));
        assertTrue(content.contains("OG"));
        assertTrue(content.contains("My Chemical Romance"));
        assertTrue(content.contains(dateInput));
    }

    @Test
    public void givenDelete_whenGameExist_thenReturnAcceptedStatus() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(delete("/api/game/{id}", 6)
                .with(user(userDetails))
            ).andReturn();

        // Arrange
        assertEquals(HttpStatus.ACCEPTED.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenDelete_whenGameDoesNotExist_thenReturnNotFoundStatus() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(delete("/api/game/{id}", 567567657)
                .with(user(userDetails))
            ).andReturn();

        // Arrange
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }
}
