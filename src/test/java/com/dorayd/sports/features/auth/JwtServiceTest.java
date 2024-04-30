package com.dorayd.sports.features.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dorayd.sports.features.auth.models.Role;
import com.dorayd.sports.features.auth.models.UserAuth;
import com.dorayd.sports.features.auth.services.JwtService;
import com.dorayd.sports.features.user.models.Gender;
import com.dorayd.sports.features.user.models.User;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class JwtServiceTest {
    
    @Autowired
    private JwtService service;

    @Test
    public void givenGenerateToken_whenGivenUserDetails_generateJsonWebToken() {
        // Arrange
        User user = new User(1L, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        UserAuth auth = new UserAuth("abc123", "password1", Role.USER, user);
        Date issueDate = new Date(100);
        Date expirationDate =  new Date(1000);
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhYmMxMjMiLCJpYXQiOjAsImV4cCI6MX0.g8Vk2UNcKNF7JJQPV7o5Posx60-62AeHalAPHCSL68Es0olet_2A0OgsSCmG94Vu";

        // Act
        String actual = service.generateToken(auth, issueDate, expirationDate);

        // Assert
        assertEquals(token, actual);
    }

    @Test
    public void givenExtractUsername_whenGivenToken_thenExtractUsername() {
        // Arrange
        UserAuth auth = new UserAuth("abc123", "password1", Role.USER, null);
        Date issueDate = new Date(System.currentTimeMillis());
        Date expirationDate =  new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        String token = service.generateToken(auth, issueDate, expirationDate);
        String expected = "abc123";

        // Act
        String actual = service.extractUsername(token);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void givenIsValid_whenTokenIsNotExpiredAndEqualToUsernameOfCredentials_thenReturnTrue() {
        // Arrange
        UserAuth auth = new UserAuth("abc123", "password1", Role.USER, null);
        Date issueDate = new Date(System.currentTimeMillis());
        Date expirationDate =  new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        String token = service.generateToken(auth, issueDate, expirationDate);

        // Act
        boolean isValid = service.isValid(token, auth);

        // Assert
        assertTrue(isValid);
    }

    @Test
    public void givenIsValid_whenTokenIsExpired_thenThrowExpiredJwtException() {
        // Arrange
        User user = new User(1L, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        UserAuth auth = new UserAuth("abc123", "password1", Role.USER, user);
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhYmMxMjMiLCJpYXQiOjAsImV4cCI6MX0.g8Vk2UNcKNF7JJQPV7o5Posx60-62AeHalAPHCSL68Es0olet_2A0OgsSCmG94Vu";

        //Act
        boolean isValid = service.isValid(token, auth);

        // Act and Assert
        assertFalse(isValid);
    }
    @Test
    public void givenIsValid_whenTokenDoesNotMatchUsername_thenReturnFalse() {
        // Arrange
        UserAuth auth = new UserAuth("abc123", "password1", Role.USER, null);
        Date issueDate = new Date(System.currentTimeMillis());
        Date expirationDate =  new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        String token = service.generateToken(auth, issueDate, expirationDate);

        // Act
        auth.setUsername("ascsacsacsacs");
        boolean isValid = service.isValid(token, auth);

        // Assert
        assertFalse(isValid);
    }

}
