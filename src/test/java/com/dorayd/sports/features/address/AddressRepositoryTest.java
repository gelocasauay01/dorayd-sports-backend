package com.dorayd.sports.features.address;

import com.dorayd.sports.features.address.models.Address;
import com.dorayd.sports.features.address.repositories.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository repository;

    @Test
    public void givenFindById_whenAddressExist_thenReturnAddress() {
        // Arrange
        final long ADDRESS_ID = 1L;
        Address expected = new Address(ADDRESS_ID,"49 Dollar St. Greenpark Village", "Brgy. San Isidro", "Cainta", "Rizal");

        // Act
        Optional<Address> actual = repository.findById(ADDRESS_ID);

        // Assert
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    public void givenFindById_whenAddressDoesNotExist_thenReturnEmptyAddress() {
        // Arrange
        final long ADDRESS_ID = 10000L;

        // Act
        Optional<Address> actual = repository.findById(ADDRESS_ID);

        // Assert
        assertTrue(actual.isEmpty());
    }

    @Test
    public void givenCreate_whenAddressIsValid_thenReturnAddress() {
        // Arrange
        Address expected = new Address(0, "48 Camella St. Greenpark Village", null, "Cainta", "Rizal");

        // Act
        Address actual = repository.create(expected);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void givenUpdate_whenAddressIsValid_thenReturnUpdatedAddress() {
        // Arrange
        final long UPDATE_ID = 2L;
        Address expected = new Address(0, "6 Bark St. Dog Subdivision", null, "Fanchucha", "Kalembong");

        // Act
        Address actual = repository.update(UPDATE_ID, expected);

        // Assert
        assertEquals(expected, actual);
    }

    @ Test
    public void givenDelete_whenAddressExists_thenReturnTrue() {
        // Arrange
        final long DELETE_ID = 3L;

        // Act
        boolean isDeleted = repository.delete(DELETE_ID);

        // Assert
        assertTrue(isDeleted);
    }

    @ Test
    public void givenDelete_whenAddressDoesNotExist_thenReturnTrue() {
        // Arrange
        final long DELETE_ID = 332423432L;

        // Act
        boolean isDeleted = repository.delete(DELETE_ID);

        // Assert
        assertFalse(isDeleted);
    }
}
