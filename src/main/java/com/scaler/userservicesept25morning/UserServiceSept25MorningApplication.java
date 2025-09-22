package com.scaler.userservicesept25morning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserServiceSept25MorningApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceSept25MorningApplication.class, args);
    }

}
