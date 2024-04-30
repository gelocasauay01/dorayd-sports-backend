package com.dorayd.sports.features.auth;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dorayd.sports.features.auth.models.AuthenticationResponse;
import com.dorayd.sports.features.auth.models.UserAuth;
import com.dorayd.sports.features.auth.services.AuthenticationService;

@RestController
@RequestMapping(AuthenticationController.AUTH_API_URL)
public class AuthenticationController {
    public final static String AUTH_API_URL = "/api/auth";

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserAuth newUserAuth) {
        try {
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.register(newUserAuth));
        } catch(DuplicateKeyException e) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody UserAuth userAuth) {
        try {
            return ResponseEntity
                .ok()
                .body(service.authenticate(userAuth));
        } catch(BadCredentialsException e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build();
        }
    }
    
}
