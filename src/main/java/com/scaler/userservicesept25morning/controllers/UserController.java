package com.scaler.userservicesept25morning.controllers;

import com.scaler.userservicesept25morning.dtos.LoginRequestDto;
import com.scaler.userservicesept25morning.dtos.SignUpRequestDto;
import com.scaler.userservicesept25morning.dtos.TokenDto;
import com.scaler.userservicesept25morning.dtos.UserDto;
import com.scaler.userservicesept25morning.exceptions.InvalidTokenException;
import com.scaler.userservicesept25morning.exceptions.PasswordMismatchException;
import com.scaler.userservicesept25morning.models.Token;
import com.scaler.userservicesept25morning.models.User;
import com.scaler.userservicesept25morning.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto requestDto) {
        User user = userService.signup(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        return UserDto.from(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto requestDto) throws PasswordMismatchException {
        String token = userService.login(
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        return token;
    }

    @GetMapping("/validate/{tokenValue}")
    public UserDto validateToken(@PathVariable("tokenValue") String tokenValue) throws InvalidTokenException {
        User user = userService.validateToken(tokenValue);

        return UserDto.from(user);
    }
}

/// amazon.in/search/iphone => PathVariable

/// amazon.in/search?q=iphone => QueryParam / RequestParam
