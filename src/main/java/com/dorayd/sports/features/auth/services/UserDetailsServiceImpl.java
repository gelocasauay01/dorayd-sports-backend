package com.dorayd.sports.features.auth.services;


import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dorayd.sports.features.auth.repositories.UserAuthRepository;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserAuthRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User with username: %s does not exist", username)));
    }
    
}
