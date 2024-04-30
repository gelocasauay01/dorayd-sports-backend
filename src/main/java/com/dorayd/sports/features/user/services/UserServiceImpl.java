package com.dorayd.sports.features.user.services;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dorayd.sports.features.user.models.User;
import com.dorayd.sports.features.user.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findById(Long id) {
        LOG.info("Finding User with id: {}", id);
        return repository.findById(id);
    }

    @Override
    public User create(User newUser) {
        LOG.info("Saving team to the database: {}", newUser);
        return repository.create(newUser);
    }

    @Override
    public User update(Long id, User updatedUser) {
        LOG.info("Updating team with id {} with {}", id, updatedUser);
        return repository.update(id, updatedUser);
    }

    @Override
    public boolean delete(Long id) {
        LOG.info("Deleting team with id {}", id);
        return repository.delete(id);
    }
}
