package com.scaler.userservicesept25morning.services;

import com.scaler.userservicesept25morning.exceptions.PasswordMismatchException;
import com.scaler.userservicesept25morning.models.Token;
import com.scaler.userservicesept25morning.models.User;
import com.scaler.userservicesept25morning.repositories.TokenRepository;
import com.scaler.userservicesept25morning.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User signup(String name, String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            //redirect to the login page.
            return optionalUser.get();
        }

        //If the email is not present in the DB then create a new user and save it to the DB.
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        //Save the user to the DB.
        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) throws PasswordMismatchException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            //redirect the user to the signUp page.
            return null;
        }

        User user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            //Password Mismatch
            throw new PasswordMismatchException("Invalid password.");
        }

        //Login Successful.
        //Generate the token.
        Token token = new Token();
        token.setUser(user);
        token.setTokenValue(RandomStringUtils.randomAlphanumeric(128));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date expiryDate = calendar.getTime();

        token.setExpiryDate(expiryDate);

        return tokenRepository.save(token);
    }

    @Override
    public User validateToken(String tokenValue) {
        return null;
    }
}
