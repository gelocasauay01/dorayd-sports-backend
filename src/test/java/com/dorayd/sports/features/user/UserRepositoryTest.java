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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.dorayd.sports.features.user.models.Gender;
import com.dorayd.sports.features.user.models.User;
import com.dorayd.sports.features.user.repositories.UserRepository;
import com.dorayd.sports.helpers.SqlQueryExecutor;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository repository;

    @Autowired 
    private JdbcTemplate jdbcTemplate;

    @Test
    public void givenFindById_whenUserExists_thenReturnSpecificUser() {
        // Arrange
        User expected = new User(1l, "Joseph", "Mardo", "Casauay", LocalDate.of(1999, 8, 1), Gender.MALE);

        // Act
        Optional<User> actual = repository.findById(1l);

        // Assert
        assertTrue(actual.isPresent());
        assertTrue(isUserEqual(expected, actual.get()));
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
        assertTrue(isUserEqual(input, actual));
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
        assertTrue(isUserEqual(update, actual));
        assertTrue(queriedAActual.isPresent());
        assertEquals(id, queriedAActual.get().getId());
        assertTrue(isUserEqual(update, queriedAActual.get()));
    }

    @Test
    public void givenDelete_whenUserExists_thenDelete() {
        // Arrange
        Long id = SqlQueryExecutor.insert("INSERT INTO users(first_name, last_name, birth_date, gender) VALUES('Kramnik', 'Jayson', '1985-01-11', 'NON_BINARY');", jdbcTemplate);

        // Act 
        boolean isDeleted = repository.delete(id);
        Optional<User> queried = repository.findById(id);

        // Assert
        assertTrue(isDeleted);
        assertFalse(queried.isPresent());
    }

    private boolean isUserEqual(User a, User b) {
        return a.getFirstName().equals(b.getFirstName())
            && a.getMiddleName().equals(b.getMiddleName())
            && a.getLastName().equals(b.getLastName())
            && a.getBirthDate().equals(b.getBirthDate())
            && a.getGender() == b.getGender();
    }
}
