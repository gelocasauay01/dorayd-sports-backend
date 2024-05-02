package com.dorayd.sports.features.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.dorayd.sports.core.test_templates.IntegrationTestWithAuthentication;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

public class UserControllerTest extends IntegrationTestWithAuthentication{

    @Test
    public void givenFindById_whenUserExists_thenReturnSpecificUser() throws Exception {
        // Arrange
        final int FIND_ID = 1;
        String expectedJson = "{\"id\":1,\"firstName\":\"Joseph\",\"middleName\":\"Mardo\",\"lastName\":\"Casauay\",\"birthDate\":\"1999-08-01\",\"gender\":\"MALE\"}";

        // Act
        MvcResult result = mockMvc.perform(get("/api/user/{id}", FIND_ID).with(user(userDetails))).andReturn();

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    public void givenFindById_whenUserDoesNotExist_thenReturnNotFoundStatus() throws Exception {
        // Act 
        MvcResult result = mockMvc.perform(get("/api/user/{id}", 100).with(user(userDetails))).andReturn();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenCreate_whenUserIsValid_thenReturnCreatedUser() throws Exception {
        // Arrange
        String expected = "{\"firstName\":\"Reynald\",\"lastName\":\"Boiser\",\"birthDate\":\"1999-08-01\",\"gender\":\"NON_BINARY\"}";

        // Act
        MvcResult result = mockMvc.perform(post("/api/user")
            .with(user(userDetails))
            .contentType(MediaType.APPLICATION_JSON)
            .content(expected)).andReturn();
        
        // Assert
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertTrue(result.getResponse().getContentAsString().contains("Boiser"));
    }

    @Test
    @DisplayName("PUT /user/{id} - OK")
    public void givenUpdate_whenUserAndIdExists_thenUpdateAndReturnUpdatedUser() throws Exception {
        // Arrange
        final int UPDATE_ID = 2;
        String expected = String.format("{\"id\":%d,\"firstName\":\"Reynald\",\"middleName\":null,\"lastName\":\"Boiser\",\"birthDate\":\"1999-08-01\",\"gender\":\"NON_BINARY\"}", UPDATE_ID);

        //Act
        MvcResult result = mockMvc.perform(put("/api/user/{id}", UPDATE_ID)
            .with(user(userDetails))
            .contentType(MediaType.APPLICATION_JSON)
            .content(expected)).andReturn();
        
        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON, MediaType.valueOf(Objects.requireNonNull(result.getResponse().getContentType())));
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void givenDelete_whenUserWithIdExists_thenDeleteUser() throws Exception {
        // Arrange
        final int DELETE_ID = 3;

        // Act
        MvcResult result = mockMvc.perform(delete("/api/user/{id}", DELETE_ID).with(user(userDetails))).andReturn();

        //Assert
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenCreate_whenInvalidNamesAreGiven_thenThrowBadRequestStatus() throws Exception {
        // Arrange
        String expected = "{\"firstName\":null,\"middleName\":\"sldjfwdkjfwdlvbdjvabdvbdkjvlsdbvljadsbvskdjlvbsdajklvbsdjkvbsdvbaekvwbeiuvwevdvbsdvbluiwevbwevjbwvjklsdbviuwevbwjvbdslvjbiewuvbwejvbsdvlkaebviwdsvjsbdvilewabiwvjsdeouiwvbwdjvbsuiaebvwjkebvsdiuvbwejvbweouvisdvjwabd\",\"lastName\":\"B\",\"birthDate\":\"1999-08-01\",\"gender\":\"NON_BINARY\"}";

        // Act
        MvcResult result = mockMvc.perform(post("/api/user")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(expected)).andReturn();


        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("First name must not be null"));
        assertTrue(result.getResponse().getContentAsString().contains("Middle name must have 3-100 characters"));
        assertTrue(result.getResponse().getContentAsString().contains("Last name must have 3-100 characters"));
    }

    @Test
    public void givenCreate_whenGenderIsNull_thenThrowBadRequestStatus() throws Exception {
        // Arrange
        String expected = "{\"firstName\":\"Reynald\",\"lastName\":\"Boiser\",\"birthDate\":\"1999-08-01\",\"gender\":null}";

        // Act
        MvcResult result = mockMvc.perform(post("/api/user")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(expected)).andReturn();


        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("Gender must not be null"));
    }

    @Test
    public void givenCreate_whenGenderIsNotInTheList_thenThrowBadRequestStatus() throws Exception {
        // Arrange
        String expected = "{\"firstName\":\"Reynald\",\"lastName\":\"Boiser\",\"birthDate\":\"1999-08-01\",\"gender\":null}";

        // Act
        MvcResult result = mockMvc.perform(post("/api/user")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(expected)).andReturn();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenCreate_whenBirthDateIsInTheFuture_thenThrowBadRequestStatus() throws Exception {
        //Arrange
        String expected = "{\"firstName\":\"Reynald\",\"lastName\":\"Boiser\",\"birthDate\":\"3000-08-01\",\"gender\":\"NON_BINARY\"}";

        // Act
        MvcResult result = mockMvc.perform(post("/api/user")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(expected)).andReturn();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("Birth date must be in the past"));
    }

    @Test
    public void givenCreate_whenBirthDateIsNull_thenThrowBadRequestStatus() throws Exception {
        //Arrange
        String expected = "{\"firstName\":\"Reynald\",\"lastName\":\"Boiser\",\"birthDate\":null,\"gender\":\"NON_BINARY\"}";

        // Act
        MvcResult result = mockMvc.perform(post("/api/user")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(expected)).andReturn();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("Birth date must not be null"));
    }
}
