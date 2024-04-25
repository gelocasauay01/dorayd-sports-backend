package com.dorayd.sports.features.user.services;

import java.util.Optional;

import com.dorayd.sports.features.user.models.User;

/**
 * This interface represents the service for managing users.
 */
public interface UserService {
    /**
     * Finds a user by its ID.
     *
     * @param id The ID of the user to find.
     * @return An Optional containing the found User, or an empty Optional if no User was found with the given ID.
     */
    Optional<User> findById(Long id);

    /**
     * Creates a new user.
     *
     * @param newUser The new User to create.
     * @return The created User.
     */
    User create(User newUser); 

    /**
     * Updates an existing user.
     *
     * @param id The ID of the user to update.
     * @param updatedUser The User entity with updated information.
     * @return The updated User.
     */
    User update(Long id, User updatedUser);

    /**
     * Deletes a user by its ID.
     *
     * @param id The ID of the user to delete.
     * @return A boolean indicating whether the deletion was successful.
     */
    boolean delete(Long id);
}