package com.dorayd.sports.features.auth.repositories;

import java.util.Optional;

import com.dorayd.sports.features.auth.models.UserAuth;

/**
 * This interface represents a repository for managing user authentication credentials.
 */
public interface UserAuthRepository {
    /**
     * Searches for a UserAuth object by username.
     *
     * @param name The email of the user to search for.
     * @return An Optional containing the found UserAuth, if present.
     */
    Optional<UserAuth> findByEmail(String name);

    /**
     * Creates a new UserAuth object.
     *
     * @param newUserAuth The UserAuth object to be created.
     * @return The created UserAuth.
     */
    UserAuth create(UserAuth newUserAuth);

    /**
     * Updates the password for a given username.
     *
     * @param password The new password.
     * @param email The email of the user for which the password should be updated.
     * @return true if the password update was successful, otherwise false.
     */
    boolean updatePassword(String password, String email);
}
