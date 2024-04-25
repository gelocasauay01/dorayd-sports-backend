package com.dorayd.sports.features.user.services;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dorayd.sports.features.user.models.User;
import com.dorayd.sports.features.user.repositories.UserRepository;

@Service
public class UserServiceimpl implements UserService{

    @Autowired
    private UserRepository repository;
    private static final Logger logger = LogManager.getLogger(UserServiceimpl.class);

    @Override
    public Optional<User> findById(Long id) {
        logger.info("Finding User with id: {}", id);
        return repository.findById(id);
    }

    @Override
    public User create(User newUser) {
        logger.info("Saving team to the database: {}", newUser);
        return repository.create(newUser);
    }

    @Override
    public User update(Long id, User updatedUser) {
        logger.info("Updating team with id {} with {}", id, updatedUser);
        return repository.update(id, updatedUser);
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Deleting team with id {}", id);
        return repository.delete(id);
    }
}
