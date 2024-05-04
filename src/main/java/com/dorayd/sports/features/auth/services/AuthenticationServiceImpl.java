package com.dorayd.sports.features.auth.services;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dorayd.sports.features.auth.models.AuthenticationResponse;
import com.dorayd.sports.features.auth.models.UserAuth;
import com.dorayd.sports.features.auth.repositories.UserAuthRepository;

@Slf4j
@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(UserAuth newUserAuth) {
        // Encode password before saving
        String encodedPassword = passwordEncoder.encode(newUserAuth.getPassword());
        newUserAuth.setPassword(encodedPassword);

        UserAuth registeredUserAuth = userAuthRepository.create(newUserAuth);

        log.info("Successfully registered user: {}", registeredUserAuth.getEmail());
        
        return new AuthenticationResponse(registeredUserAuth.getUser(), generateTokenWithNowIssueDateAndTomorrowExpiration(registeredUserAuth));
    }

    @Override
    public AuthenticationResponse authenticate(UserAuth userAuthFromRequest) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userAuthFromRequest.getEmail(), 
            userAuthFromRequest.getPassword()
        );
        authenticationManager.authenticate(authentication);

        log.info("Successfully authenticated user: {}", userAuthFromRequest.getEmail());

        UserAuth userAuth = userAuthRepository.findByEmail(userAuthFromRequest.getEmail()).orElseThrow();
        return new AuthenticationResponse(userAuth.getUser(),  generateTokenWithNowIssueDateAndTomorrowExpiration(userAuth));
    }

    private String generateTokenWithNowIssueDateAndTomorrowExpiration(UserAuth userAuth) {
        Date now = new Date(System.currentTimeMillis());
        Date tomorrow =  new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        return jwtService.generateToken(userAuth, now, tomorrow);
    }
    
}
