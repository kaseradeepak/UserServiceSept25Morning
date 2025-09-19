package com.scaler.userservicesept25morning.controllers;

import com.scaler.userservicesept25morning.dtos.LoginRequestDto;
import com.scaler.userservicesept25morning.dtos.SignUpRequestDto;
import com.scaler.userservicesept25morning.dtos.TokenDto;
import com.scaler.userservicesept25morning.dtos.UserDto;
import com.scaler.userservicesept25morning.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto requestDto) {
        return null;
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginRequestDto requestDto) {
        return null;
    }

    @GetMapping("/validate/{tokenValue}")
    public UserDto validateToken(@PathVariable("tokenValue") String tokenValue) {
        return null;
    }
}

/// amazon.in/search/iphone => PathVariable

/// amazon.in/search?q=iphone => QueryParam / RequestParam
