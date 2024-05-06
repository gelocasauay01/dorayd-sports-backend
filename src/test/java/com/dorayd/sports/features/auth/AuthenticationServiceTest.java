package com.dorayd.sports.features.auth;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.dorayd.sports.features.auth.responses.AuthenticationResponse;
import com.dorayd.sports.features.auth.models.Role;
import com.dorayd.sports.features.auth.models.UserAuth;
import com.dorayd.sports.features.auth.services.AuthenticationService;
import com.dorayd.sports.features.user.models.Gender;
import com.dorayd.sports.features.user.models.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AuthenticationServiceTest {
    
    @Autowired
    AuthenticationService service;

    @Test
    public void givenRegister_whenCredentialsAreValidAndUserDoesNotExist_thenSaveAndReturnAuthenticationResponse() {
        // Arrange
        User expectedUser = new User(0, "Joseph", "Bryan", "Benington", LocalDate.of(1999, 8, 1), Gender.FEMALE);
        UserAuth userAuth = new UserAuth("asdf123456456457@yahoo.com", "password890", Role.USER, expectedUser);

        // Act
        AuthenticationResponse response = service.register(userAuth);

        // Assert
        assertEquals(expectedUser, response.user());
        assertFalse(response.token().isBlank());
    }

    @Test
    public void givenRegister_whenEmailExists_thenThrowDuplicateKeyException() {
        // Arrange
        User expectedUser = new User(0, "Joseph", "Bryan", "Benington", LocalDate.of(1999, 8, 1), Gender.FEMALE);
        UserAuth expectedUserAuth = new UserAuth("abc123@yahoo.com", "password1", Role.USER, expectedUser);

        // Act and Assert
        assertThrows(DuplicateKeyException.class, () -> service.register(expectedUserAuth));
    }

    @Test
    public void givenLogin_whenUserExists_thenReturnAuthenticationResponse(){ 
        // Arrange
        User expectedUser = new User(1L, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        UserAuth expectedUserAuth = new UserAuth("abc123@yahoo.com", "password1", Role.USER, expectedUser);

        // Act
        AuthenticationResponse response = service.authenticate(expectedUserAuth);

        // Assert
        assertEquals(expectedUser, response.user());
        assertFalse(response.token().isBlank());
    }

    @Test
    public void givenLogin_whenUserDoesNotExist_thenThrowBadCredentialsException(){ 
        // Arrange
        UserAuth expectedUserAuth = new UserAuth("abc12sdvsdv3@yahoo.com", "password1sacsacsa", Role.USER, null);

        // Act
        assertThrows(BadCredentialsException.class, () -> service.authenticate(expectedUserAuth));
    }

}
