package com.dorayd.sports.features.auth.responses;

import com.dorayd.sports.features.user.models.User;

public record AuthenticationResponse(User user, String token) {}
