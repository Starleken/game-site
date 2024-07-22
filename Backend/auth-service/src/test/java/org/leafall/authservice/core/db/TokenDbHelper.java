package org.leafall.authservice.core.db;

import lombok.RequiredArgsConstructor;
import org.leafall.authservice.core.utils.entity.UserEntityUtils;
import org.leafall.authservice.entity.TokenEntity;
import org.leafall.authservice.entity.TokenEntityStatus;
import org.leafall.authservice.repository.TokenRepository;
import org.leafall.authservice.repository.UserRepository;
import org.leafall.authservice.service.TokenService;
import org.springframework.stereotype.Component;

import static org.leafall.authservice.core.utils.entity.UserEntityUtils.*;
import static org.leafall.authservice.utils.ExceptionUtils.getEntityNotFoundException;

@Component
@RequiredArgsConstructor
public class TokenDbHelper {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public TokenEntity findByToken(String token) {
        return tokenRepository.findByToken(token)
                .orElseThrow(() -> getEntityNotFoundException(TokenEntity.class));
    }

    public String saveToken() {
        var user = userRepository.save(generateUser());
        var token = tokenService.generateRefreshToken(user.getId());

        return token;
    }

    public String saveToken(long createdAt) {
        var user = userRepository.save(generateUser());
        var token = tokenService.generateRefreshToken(user.getId());

        var entity = tokenRepository.findByToken(token)
                .orElseThrow(() -> getEntityNotFoundException(TokenEntity.class));
        entity.setCreatedAt(createdAt);

        tokenRepository.save(entity);

        return token;
    }

    public String saveToken(TokenEntityStatus status) {
        var user = userRepository.save(generateUser());
        var token = tokenService.generateRefreshToken(user.getId());

        var tokenEntity = findByToken(token);
        tokenEntity.setStatus(status);
        tokenRepository.save(tokenEntity);

        return token;
    }

    public void clear() {
        tokenRepository.deleteAll();
        userRepository.deleteAll();
    }
}
