package com.scaler.userservicesept25morning.services;

import com.scaler.userservicesept25morning.models.Token;
import com.scaler.userservicesept25morning.models.User;

public interface UserService {
    User signup(String name, String email, String password);

    Token login(String email, String password);

    User validateToken(String tokenValue);
}
