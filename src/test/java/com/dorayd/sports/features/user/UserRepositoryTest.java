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
        User expected = new User(1L, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);

        // Act
        Optional<User> actual = repository.findById(1L);

        // Assert
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    public void givenFindById_whenUserDoesNotExists_thenReturnNotFoundStatus() {
        // Act
        Optional<User> actual = repository.findById(10000L);

        // Assert
        assertTrue(actual.isEmpty());
    }

    @Test 
    public void givenCreate_whenUserIsValid_thenReturnCreatedUser() {
        // Arrange
        User input = new User(null, "Joseph", "Bryan", "Benington", LocalDate.of(1999, 8, 1), Gender.FEMALE);

        // Act
        User actual = repository.create(input);

        // Assert 
        assertNotNull(actual.getId());
        assertTrue(actual.getId().compareTo(0L) > 0);
        assertEquals(input, actual);
    }

    @Test
    public void givenUpdate_whenUserExists_thenUpdateUserAndReturn() {
        // Arrange
        User update = new User(null, "Jones", "Hayley", "Benington", LocalDate.of(1999, 8, 1), Gender.FEMALE);
        Long updateId = 2L;

        // Act
        User actual = repository.update(updateId, update);
        Optional<User> queriedAActual = repository.findById(actual.getId());

        // Assert
        assertEquals(updateId, actual.getId());
        assertEquals(update, actual);
        assertTrue(queriedAActual.isPresent());
        assertEquals(updateId, queriedAActual.get().getId());
        assertEquals(update, queriedAActual.get());
    }

    @Test
    public void givenDelete_whenUserExists_thenDelete() {
        // Arrange
        final long deleteId = 4L;

        // Act 
        boolean isDeleted = repository.delete(deleteId);
        Optional<User> queried = repository.findById(deleteId);

        // Assert
        assertTrue(isDeleted);
        assertFalse(queried.isPresent());
    }
}
