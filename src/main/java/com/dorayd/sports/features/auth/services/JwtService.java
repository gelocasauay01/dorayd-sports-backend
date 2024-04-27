package com.dorayd.sports.features.auth.services;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;


/**
 * The JwtService interface provides methods for working with JSON Web Tokens (JWTs).
 */
public interface JwtService {

    /**
     * Generates a JWT token for the specified user details.
     *
     * @param userDetails   The UserDetails object containing the user's details.
     * @param issueDate     The date when the token is issued.
     * @param expirationDate The date when the token expires.
     * @return A string representing the generated JWT token.
     */
    String generateToken(UserDetails userDetails, Date issueDate, Date expirationDate);

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param token The JWT token from which to extract the username.
     * @return A string representing the username extracted from the token.
     */
    String extractUsername(String token);

    /**
     * Checks whether a JWT token is valid for the specified user details.
     *
     * @param token       The JWT token to validate.
     * @param userDetails The UserDetails object representing the user's details.
     * @return true if the token is valid for the user details, false otherwise.
     */
    boolean isValid(String token, UserDetails userDetails);
}
