package com.dorayd.sports.features.auth.services;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dorayd.sports.features.auth.repositories.UserAuthRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserAuthRepository repository;

    public UserDetailsServiceImpl(UserAuthRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User with username: %s does not exist", username)));
    }
    
}
