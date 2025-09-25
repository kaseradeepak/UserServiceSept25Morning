package com.scaler.userservicesept25morning.services;

import com.scaler.userservicesept25morning.exceptions.InvalidTokenException;
import com.scaler.userservicesept25morning.exceptions.PasswordMismatchException;
import com.scaler.userservicesept25morning.models.Role;
import com.scaler.userservicesept25morning.models.User;
import com.scaler.userservicesept25morning.repositories.RoleRepository;
import com.scaler.userservicesept25morning.repositories.TokenRepository;
import com.scaler.userservicesept25morning.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;
    private RoleRepository roleRepository;
    private SecretKey secretKey;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           TokenRepository tokenRepository,
                           RoleRepository roleRepository,
                           SecretKey secretKey) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.secretKey = secretKey;
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

        Optional<Role> optionalRole = roleRepository.findByValue("STUDENT");
        user.getRoles().add(optionalRole.get());

        //Save the user to the DB.
        return userRepository.save(user);
    }

    @Override
    public String login(String email, String password) throws PasswordMismatchException {
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

//        Token token = new Token();
//        token.setUser(user);
//        token.setTokenValue(RandomStringUtils.randomAlphanumeric(128));
//
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date expiryDate = calendar.getTime();
//
//        token.setExpiryDate(expiryDate);

        //Generate a JWT token using JJWT.

        //Let's not hard code the payload, instead create the payload with the user details.
//        String userData = "{\n" +
//                "   \"email\": \"deepak@gmail.com\",\n" +
//                "   \"roles\": [\n" +
//                "      \"instructor\",\n" +
//                "      \"ta\"\n" +
//                "   ],\n" +
//                "   \"expiryDate\": \"22ndSept2026\"\n" +
//                "}";

        //TODO: Try to generate header & signature.

        // Claims == Payload.

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("exp", expiryDate);

//        MacAlgorithm algorithm = Jwts.SIG.HS256;
//        SecretKey secretKey =  algorithm.key().build();

        // byte[] payload = userData.getBytes(StandardCharsets.UTF_8);
        // String tokenValue = Jwts.builder().content(payload).compact();
        String jwtToken = Jwts.builder().claims(claims).signWith(secretKey).compact();

        return jwtToken;
    }

    @Override
    public User validateToken(String jwtToken) throws InvalidTokenException {

//        Optional<Token> tokenOptional =
//                tokenRepository.findByTokenValueAndExpiryDateAfter(tokenValue, new Date());
//
//        if (tokenOptional.isEmpty()) {
//            //token is invalid or either expired.
//            //throw new InvalidTokenException("Invalid token, either the tokenValue is invalid or token has expired.");
//            return null;
//        }

//        MacAlgorithm algorithm = Jwts.SIG.HS256;
//        SecretKey secretKey =  algorithm.key().build();


        /*
        1. Create a parser.
        2. Get the claims and verify the token
         */

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(jwtToken).getPayload();

        // JWT stores the exp time in epoch format.
//        Date expiryDate = (Date) claims.get("exp");
//        Date currentDate = new Date();

        Long expiryTime = (Long) claims.get("exp");
        expiryTime = expiryTime * 1000L; // convert the expiry time into milliseconds.
        Long currentTime = System.currentTimeMillis(); // epoch time.

        if (expiryTime > currentTime) {
            //Token is valid
            Integer userId = (Integer) claims.get("userId");

            Optional<User> optionalUser = userRepository.findById(Long.valueOf(userId));

            return optionalUser.get();
        }

        //Token is InValid.
        return null;
    }
}