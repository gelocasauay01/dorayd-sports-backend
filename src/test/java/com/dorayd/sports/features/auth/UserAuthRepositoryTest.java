package com.dorayd.sports.features.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.dorayd.sports.features.auth.models.Role;
import com.dorayd.sports.features.auth.models.UserAuth;
import com.dorayd.sports.features.auth.repositories.UserAuthRepository;
import com.dorayd.sports.features.user.models.Gender;
import com.dorayd.sports.features.user.models.User;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserAuthRepositoryTest {
    
    @Autowired
    private UserAuthRepository repository;


    @Test
    public void givenFindByUsername_whenUserExist_thenReturnUserAuth() {
        // Arrange
        User expectedUser = new User(1l, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        UserAuth expectedUserAuth = new UserAuth("abc123@yahoo.com", "$2a$10$N35fUCHQ7/OwM4Dcw6LH8uwL8yFIJ/PnoxgAuVDJEUuNlXGANmu1G", Role.USER, expectedUser);

        // Act
        Optional<UserAuth> actual = repository.findByEmail("abc123@yahoo.com");

        // Assert
        assertEquals(expectedUserAuth, actual.get());
    }

    @Test
    public void givenFindById_whenUserDoesExists_thenReturnNotFoundStatus() {
        // Act
        Optional<UserAuth> actual = repository.findByEmail("testuser@yahoo.com");

        // Assert
        assertTrue(actual.isEmpty());
    }

    @Test 
    public void givenCreate_whenUserAuthIsValidAndUserExist_thenReturnCreatedUserAuth() {
        // Arrange
        User expectedUser = new User(4l, "Hayley", "Mark", "Jones", LocalDate.of(1985, 1, 11), Gender.FEMALE);
        UserAuth expectedUserAuth = new UserAuth("asdf123436346@yahoo.com", "password890", Role.USER, expectedUser);

        // Act
        UserAuth actual = repository.create(expectedUserAuth);

        // Assert
        assertEquals(expectedUserAuth, actual);
    }

    @Test 
    public void givenCreate_whenUserAuthIsValidAndUserDoesNotExist_thenReturnCreatedUserAuth() {
        // Arrange
        User expectedUser = new User(0l, "Hayleyascsacsa", "ascsacMark", "Jonascsaces", LocalDate.of(1985, 1, 11), Gender.FEMALE);
        UserAuth expectedUserAuth = new UserAuth("asdf123436345435435346@yahoo.com", "password890", Role.USER, expectedUser);

        // Act
        UserAuth actual = repository.create(expectedUserAuth);

        // Assert
        assertEquals(expectedUserAuth, actual);
    }

    @Test
    public void givenUpdatePasswordWithUsername_whenUserAuthWithUsernameExist_thenUpdatePassword() {
        // Arrange
        String expected = "newpassword";
        String email = "abc456@yahoo.com";

        // Act
        boolean isUpdated = repository.updatePassword(expected, email);
        UserAuth userAuth = repository.findByEmail(email).orElseThrow();

        // Assert
        assertTrue(isUpdated);
        assertEquals(expected, userAuth.getPassword());
    }

    @Test
    public void givenUpdatePasswordWithUsername_whenUserAuthWithUsernameDoesNotExist_thenDoNotUpdatePassword() {
        // Arrange
        String expected = "newpassword";
        String email = "abcascascsac@yahoo.com";

        // Act
        boolean isUpdated = repository.updatePassword(expected, email);

        // Assert
        assertFalse(isUpdated);
    }

}
