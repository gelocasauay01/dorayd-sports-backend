package com.dorayd.sports.features.user;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dorayd.sports.features.user.models.User;
import com.dorayd.sports.features.user.services.UserService;

@RestController
@RequestMapping(UserController.USER_API_URL)
public class UserController {
    public static final String USER_API_URL = "/api/user";

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        Optional<User> user = service.findById(id);

        return user.map(value -> ResponseEntity
                .ok()
                .body(value)).orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build());

    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User newUser) {
        User createdUser = service.create(newUser);
        try {
            return ResponseEntity
                .created(new URI(String.format("%s/%d", USER_API_URL, createdUser.getId())))
                .body(createdUser);
        } catch(URISyntaxException e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody User updatedUser) {
        return ResponseEntity
            .ok()
            .body(service.update(id, updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if(service.delete(id)) {
            return ResponseEntity
                .ok()
                .build();
        } else {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
        }
    }
    
}