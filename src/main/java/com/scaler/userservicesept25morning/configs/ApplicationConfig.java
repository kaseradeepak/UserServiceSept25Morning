package com.scaler.userservicesept25morning.configs;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;

@Configuration
public class ApplicationConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf().disable();
//        httpSecurity.cors().disable();
//        httpSecurity.authorizeHttpRequests(
//                authorize -> authorize.anyRequest().permitAll()
//        );
//
//        return httpSecurity.build();
//    }

    @Bean
    public SecretKey getSecretKey() {
        MacAlgorithm algorithm = Jwts.SIG.HS256;
        SecretKey secretKey =  algorithm.key().build();
        return secretKey;
    }
}
