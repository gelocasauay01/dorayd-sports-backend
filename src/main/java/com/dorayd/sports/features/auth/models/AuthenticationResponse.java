package com.dorayd.sports.features.auth.models;

import com.dorayd.sports.features.user.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {
    private User user;
    private String token;
}
