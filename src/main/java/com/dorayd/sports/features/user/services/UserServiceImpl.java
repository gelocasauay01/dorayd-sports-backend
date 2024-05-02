package com.dorayd.sports.features.user.services;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.dorayd.sports.features.user.models.User;
import com.dorayd.sports.features.user.repositories.UserRepository;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository repository;

    @Override
    public Optional<User> findById(Long id) {
        log.info("Finding User with id: {}", id);
        return repository.findById(id);
    }

    @Override
    public User create(User newUser) {
        log.info("Saving team to the database: {}", newUser);
        return repository.create(newUser);
    }

    @Override
    public User update(Long id, User updatedUser) {
        log.info("Updating team with id {} with {}", id, updatedUser);
        return repository.update(id, updatedUser);
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting team with id {}", id);
        return repository.delete(id);
    }
}
