package com.scaler.userservicesept25morning.repositories;

import com.scaler.userservicesept25morning.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Override
    Optional<Role> findById(Long aLong);

    Optional<Role> findByValue(String value);
}
