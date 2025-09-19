package com.scaler.userservicesept25morning.repositories;

import com.scaler.userservicesept25morning.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Override
    Token save(Token token);
}
