package com.sonar.authentication.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthToken,Integer> {
    AuthToken findByUser(User user);
}

