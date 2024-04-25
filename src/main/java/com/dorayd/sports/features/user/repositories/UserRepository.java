package com.dorayd.sports.features.user.repositories;

import java.util.Optional;

import com.dorayd.sports.features.user.models.User;

/**
 * This interface represents a repository for managing Users.
 */
public interface UserRepository {
    /**
     * Finds a User by its ID.
     *
     * @param id The ID of the User to find.
     * @return An Optional containing the found User, or an empty Optional if no User was found with the given ID.
     */
    Optional<User> findById(Long id);

    /**
     * Creates a new User.
     *
     * @param newUser The User to create.
     * @return The created User.
     */
    User create(User newUser);

    /**
     * Updates an existing User.
     *
     * @param id The ID of the User to update.
     * @param updatedUser The User entity with updated information.
     * @return The updated User.
     */
    User update(Long id, User updatedUser);

    /**
     * Deletes a User by its ID.
     *
     * @param id The ID of the User to delete.
     * @return A boolean indicating whether the deletion was successful.
     */
    boolean delete(Long id);
}