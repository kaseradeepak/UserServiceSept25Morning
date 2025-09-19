package com.scaler.userservicesept25morning.services;

import com.scaler.userservicesept25morning.models.Token;
import com.scaler.userservicesept25morning.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User signup(String name, String email, String password) {
        return null;
    }

    @Override
    public Token login(String email, String password) {
        return null;
    }

    @Override
    public User validateToken(String tokenValue) {
        return null;
    }
}
