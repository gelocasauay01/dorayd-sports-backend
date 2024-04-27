package com.dorayd.sports.features.auth.services;

import com.dorayd.sports.features.auth.models.AuthenticationResponse;
import com.dorayd.sports.features.auth.models.UserAuth;

/**
 * The AuthenticationService interface provides methods for user authentication and registration.
 */
public interface AuthenticationService {

    /**
     * Registers a new user with the system.
     *
     * @param newUser The UserAuth object containing the new user's authentication information.
     * @return An AuthenticationResponse object indicating the result of the registration process.
     */
    AuthenticationResponse register(UserAuth newUser);

    /**
     * Authenticates a user based on the provided authentication information.
     *
     * @param userAuth The UserAuth object containing the user's authentication credentials.
     * @return An AuthenticationResponse object indicating the result of the authentication process.
     */
    AuthenticationResponse authenticate(UserAuth userAuth);
}

