package com.dorayd.sports.features.auth.services;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dorayd.sports.features.auth.models.AuthenticationResponse;
import com.dorayd.sports.features.auth.models.UserAuth;
import com.dorayd.sports.features.auth.repositories.UserAuthRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserAuthRepository userAuthRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userAuthRepository = userAuthRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    private static final Logger LOG = LogManager.getLogger(AuthenticationServiceImpl.class);

    @Override
    public AuthenticationResponse register(UserAuth newUserAuth) {
        // Encode password before saving
        String encodedPassword = passwordEncoder.encode(newUserAuth.getPassword());
        newUserAuth.setPassword(encodedPassword);

        UserAuth registeredUserAuth = userAuthRepository.create(newUserAuth);

        LOG.info("Successfully registered user: {}", registeredUserAuth.getUsername());
        
        return new AuthenticationResponse(registeredUserAuth.getUser(), generateTokenWithNowIssueDateAndTomorrowExpiration(registeredUserAuth));
    }

    @Override
    public AuthenticationResponse authenticate(UserAuth userAuthFromRequest) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userAuthFromRequest.getUsername(), 
            userAuthFromRequest.getPassword()
        );
        authenticationManager.authenticate(authentication);

        LOG.info("Successfully authenticated user: {}", userAuthFromRequest.getUsername());

        UserAuth userAuth = userAuthRepository.findByUsername(userAuthFromRequest.getUsername()).get();
        return new AuthenticationResponse(userAuth.getUser(),  generateTokenWithNowIssueDateAndTomorrowExpiration(userAuth));
    }

    private String generateTokenWithNowIssueDateAndTomorrowExpiration(UserAuth userAuth) {
        Date now = new Date(System.currentTimeMillis());
        Date tomorrow =  new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        return jwtService.generateToken(userAuth, now, tomorrow);
    }
    
}
