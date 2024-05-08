package com.dorayd.sports.features.game_stats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.dorayd.sports.core.test_templates.IntegrationTestWithAuthentication;

public class GameStatsControllerTest extends IntegrationTestWithAuthentication{
    
    @Test
    public void givenCreate_whenNewStatsIsValid_thensaveAndReturnGameStats() throws Exception {
        // Arrange
        String requestBody = """
            {
                "userId": 1,
                "gameId": 5,
                "points": 10,
                "assists": 5
            }
        """;

        // Act
        MvcResult result = mockMvc.perform(post("/api/game-stats")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON)
            .with(user(userDetails)))
            .andReturn();
        String content = result.getResponse().getContentAsString();

        // Assert
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertTrue(content.contains("\"points\":10"));
        assertTrue(content.contains("\"assists\":5"));
    }

    @Test
    public void givenGetByUserIdAndGameId_whenGameStatsExist_thenReturnPlayerStatsOfThatSpecificGame() throws Exception{
        // Arrange
        final long USER_ID = 1;
        final long GAME_ID = 2;

        // Act
        MvcResult result = mockMvc.perform(get("/api/game-stats?userId={userId}&gameId={gameId}", USER_ID, GAME_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .with(user(userDetails)))
            .andReturn();
        String content = result.getResponse().getContentAsString();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertTrue(content.contains("\"points\":13"));
        assertTrue(content.contains("\"assists\":2"));
        assertTrue(content.contains("\"rebounds\":2"));
    }

    @Test
    public void givenUpdateByUserIdAndGameId_whenGameWithUserIdExists_thenUpdateAndReturnGameStats() throws Exception {
        // Arrange
        String requestBody = """
            {
                "userId": 2,
                "gameId": 4,
                "points": 10,
                "assists": 5
            }
        """;

        // Act
        MvcResult result = mockMvc.perform(put("/api/game-stats")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON)
            .with(user(userDetails)))
            .andReturn();
        String content = result.getResponse().getContentAsString();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertTrue(content.contains("\"points\":10"));
        assertTrue(content.contains("\"assists\":5"));
        assertFalse(content.contains("\"points\":13"));
        assertFalse(content.contains("\"assists\":2"));
    }

    @Test
    public void givenDeleteByUserIdAndGameId_whenUserExistInAGame_thenDeleteAndReturnTrue() throws Exception {
        // Arrange
        final long USER_ID = 2;
        final long GAME_ID = 2;

        // Act
        MvcResult result = mockMvc.perform(delete("/api/game-stats?userId={userId}&gameId={gameId}", USER_ID, GAME_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .with(user(userDetails)))
            .andReturn();
        String content = result.getResponse().getContentAsString();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertTrue(content.contains("true"));
    }
}
