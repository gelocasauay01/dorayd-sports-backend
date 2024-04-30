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
        User expectedUser = new User(1L, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);
        UserAuth expectedUserAuth = new UserAuth("abc123", "$2a$10$N35fUCHQ7/OwM4Dcw6LH8uwL8yFIJ/PnoxgAuVDJEUuNlXGANmu1G", Role.USER, expectedUser);

        // Act
        Optional<UserAuth> actual = repository.findByUsername("abc123");

        // Assert
        assertTrue(actual.isPresent());
        assertTrue(isUserAuthEqual(expectedUserAuth, actual.get()));
    }

    @Test
    public void givenFindById_whenUserDoesExists_thenReturnNotFoundStatus() {
        // Act
        Optional<UserAuth> actual = repository.findByUsername("testuser");

        // Assert
        assertTrue(actual.isEmpty());
    }

    @Test 
    public void givenSave_whenUserAuthIsValidAndUserDoesNotExist_thenReturnCreatedUserAuth() {
        // Arrange
        User expectedUser = new User(null, "Joseph", "Bryan", "Benington", LocalDate.of(1999, 8, 1), Gender.FEMALE);
        UserAuth expectedUserAuth = new UserAuth("asdf123", "password890", Role.USER, expectedUser);

        // Act
        UserAuth actual = repository.create(expectedUserAuth);

        // Assert
        assertTrue(isUserAuthEqual(expectedUserAuth, actual));
        assertNotNull(actual.getUser().getId());
    }

    @Test 
    public void givenSave_whenUserAuthIsValidAndUserExist_thenReturnCreatedUserAuth() {
        // Arrange
        User expectedUser = new User(4L, "Hayley", "Mark", "Jones", LocalDate.of(1985, 1, 11), Gender.FEMALE);
        UserAuth expectedUserAuth = new UserAuth("asdf123436346", "password890", Role.USER, expectedUser);

        // Act
        UserAuth actual = repository.create(expectedUserAuth);

        // Assert
        assertTrue(isUserAuthEqual(expectedUserAuth, actual));
    }

    @Test
    public void givenUpdatePasswordWithUsername_whenUserAuthWithUsernameExist_thenUpdatePassword() {
        // Arrange
        String expected = "newpassword";
        String username = "abc456";

        // Act
        boolean isUpdated = repository.updatePassword(expected, username);
        UserAuth userAuth = repository.findByUsername(username).orElseThrow();

        // Assert
        assertTrue(isUpdated);
        assertEquals(expected, userAuth.getPassword());
    }

    @Test
    public void givenUpdatePasswordWithUsername_whenUserAuthWithUsernameDoesNotExist_thenDoNotUpdatePassword() {
        // Arrange
        String expected = "newpassword";
        String username = "abcascascsac";

        // Act
        boolean isUpdated = repository.updatePassword(expected, username);

        // Assert
        assertFalse(isUpdated);
    }

    private boolean isUserAuthEqual(UserAuth a, UserAuth b) {
        return a.getUsername().equals(b.getUsername())
            && a.getPassword().equals(b.getPassword())
            && a.getRole() == b.getRole()
            && isUserEqual(a.getUser(), b.getUser());
    }

    private boolean isUserEqual(User a, User b) {
        return a.getFirstName().equals(b.getFirstName())
            && a.getMiddleName().equals(b.getMiddleName())
            && a.getLastName().equals(b.getLastName())
            && a.getBirthDate().equals(b.getBirthDate())
            && a.getGender() == b.getGender();
    }

}
