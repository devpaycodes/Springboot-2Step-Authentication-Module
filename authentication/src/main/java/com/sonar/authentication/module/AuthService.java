package com.sonar.authentication.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    TokenRepository tokenRepository;

    public void saveConfirmationToken(AuthToken authToken) {
        tokenRepository.save(authToken);
    }

    public AuthToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }
}
