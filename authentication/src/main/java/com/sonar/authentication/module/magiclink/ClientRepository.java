package com.sonar.authentication.module.magiclink;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Integer> {

    Client findByToken(String token);
}
