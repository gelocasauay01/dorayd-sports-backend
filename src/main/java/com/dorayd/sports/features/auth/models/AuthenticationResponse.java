package com.dorayd.sports.features.auth.models;

import com.dorayd.sports.features.user.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthenticationResponse {
    private User user;
    private String token;
}
