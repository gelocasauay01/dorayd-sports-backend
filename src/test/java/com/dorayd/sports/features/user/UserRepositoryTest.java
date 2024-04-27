package com.dorayd.sports.features.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.dorayd.sports.features.user.models.Gender;
import com.dorayd.sports.features.user.models.User;
import com.dorayd.sports.features.user.repositories.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository repository;

    @Test
    public void givenFindById_whenUserExists_thenReturnSpecificUser() {
        // Arrange
        User expected = new User(1l, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);

        // Act
        Optional<User> actual = repository.findById(1l);

        // Assert
        assertTrue(actual.isPresent());
        assertTrue(UserTestHelper.isUserEqual(expected, actual.get()));
    }

    @Test
    public void givenFindById_whenUserDoesExists_thenReturnNotFoundStatus() {
        // Act
        Optional<User> actual = repository.findById(10l);

        // Assert
        assertTrue(!actual.isPresent());
    }

    @Test 
    public void givenCreate_whenUserIsValid_thenReturnCreatedUser() {
        // Arrange
        User input = new User(null, "Joseph", "Bryan", "Benington", LocalDate.of(1999, 8, 1), Gender.FEMALE);

        // Act
        User actual = repository.create(input);

        // Assert 
        assertNotNull(actual.getId());
        assertTrue(actual.getId().compareTo(0l) > 0);
        assertTrue(UserTestHelper.isUserEqual(input, actual));
    }

    @Test
    public void givenUpdate_whenUserExists_thenUpdateUserAndReturn() {
        // Arrange
        User update = new User(null, "Jones", "Hayley", "Benington", LocalDate.of(1999, 8, 1), Gender.FEMALE);
        Long id = 2l;

        // Act
        User actual = repository.update(id, update);
        Optional<User> queriedAActual = repository.findById(actual.getId());

        // Assert
        assertEquals(id, actual.getId());
        assertTrue(UserTestHelper.isUserEqual(update, actual));
        assertTrue(queriedAActual.isPresent());
        assertEquals(id, queriedAActual.get().getId());
        assertTrue(UserTestHelper.isUserEqual(update, queriedAActual.get()));
    }

    @Test
    public void givenDelete_whenUserExists_thenDelete() {
        // Arrange
        final long DELETE_ID = 4l;

        // Act 
        boolean isDeleted = repository.delete(DELETE_ID);
        Optional<User> queried = repository.findById(DELETE_ID);

        // Assert
        assertTrue(isDeleted);
        assertFalse(queried.isPresent());
    }
}
