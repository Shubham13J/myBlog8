package com.myblog8.repository;

import com.myblog8.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String UserName);
    Optional<User> findByUsernameOrEmail(String Username, String email);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
