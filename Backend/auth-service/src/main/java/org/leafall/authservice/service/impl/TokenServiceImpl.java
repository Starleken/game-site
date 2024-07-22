package org.leafall.authservice.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.leafall.authservice.dto.token.RefreshTokenResponseDto;
import org.leafall.authservice.entity.TokenEntity;
import org.leafall.authservice.entity.TokenEntityStatus;
import org.leafall.authservice.entity.UserEntity;
import org.leafall.authservice.exception.TokenIsUsedException;
import org.leafall.authservice.repository.TokenRepository;
import org.leafall.authservice.repository.UserRepository;
import org.leafall.authservice.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Set;

import static org.leafall.authservice.entity.TokenEntityStatus.*;
import static org.leafall.authservice.utils.ExceptionUtils.*;
import static org.leafall.authservice.utils.TimeUtils.getCurrentTimeFromUTC;
import static org.leafall.authservice.utils.TimeUtils.getExpiredDateFromUTC;

@Service
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    private final SecretKey secretAccessKey;
    private final SecretKey secretRefreshKey;

    private final long accessTime;
    private final long refreshTime;

    public TokenServiceImpl(@Autowired TokenRepository tokenRepository,
                            @Autowired UserRepository userRepository,
                            @Value("${jwt.access.secretKey}") String secretAccessKey,
                            @Value("${jwt.refresh.secretKey}") String secretRefreshKey,
                            @Value("${jwt.access.ttlMinutes}") long accessTime,
                            @Value("${jwt.refresh.ttlMinutes}") long refreshTime) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.secretAccessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretAccessKey));
        this.secretRefreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretRefreshKey));
        this.accessTime = accessTime;
        this.refreshTime = refreshTime;
    }

    @Override
    @Transactional
    public String generateAccessToken(long userId) {
        Date expirationTime = getExpiredDateFromUTC(accessTime);

        var foundUser = userRepository.findById(userId)
                .orElseThrow(() -> getEntityNotFoundException(UserEntity.class, userId));

        return generateToken(foundUser.getId(), secretAccessKey, expirationTime);
    }

    @Override
    @Transactional
    public String generateRefreshToken(long userId) {
        Date expirationTime = getExpiredDateFromUTC(refreshTime);

        var foundUser = userRepository.findById(userId).orElseThrow(
                () -> getEntityNotFoundException(UserEntity.class, userId));

        var refresh = generateToken(foundUser.getId(), secretRefreshKey, expirationTime);

        var tokenEntity = new TokenEntity();
        tokenEntity.setToken(refresh);
        tokenEntity.setUser(foundUser);
        tokenEntity.setStatus(ISSUED);
        tokenEntity.setCreatedAt(getCurrentTimeFromUTC());
        tokenRepository.save(tokenEntity);

        return refresh;
    }

    private String generateToken(long userId, SecretKey key, Date expirationTime) {
        var foundUser = userRepository.findById(userId)
                .orElseThrow(() -> getEntityNotFoundException(UserEntity.class, userId));

        return Jwts.builder()
                .setSubject(foundUser.getId().toString())
                .setExpiration(expirationTime)
                .claim("role", foundUser.getRole().name())
                .signWith(key)
                .compact();
    }

    @Override
    public void validateAccessToken(String token) {
        validateToken(secretAccessKey, token);
    }

    @Override
    @Transactional(noRollbackFor = TokenIsUsedException.class)
    public void validateRefreshToken(String token) {
        validateToken(secretRefreshKey, token);

        var foundToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> getEntityNotFoundException(TokenEntity.class));

        var foundUser = userRepository.findByEmail(foundToken.getUser().getEmail())
                .orElseThrow(() -> getEntityNotFoundException(UserEntity.class));

        if (foundToken.getStatus() == USED) {
            var tokens = tokenRepository.findAllByUser(foundUser);
            tokens.forEach(x -> x.setStatus(WITHDRAWN));

            tokenRepository.saveAll(tokens);
            throwTokenIsUsedException("Token is used");
        }

        if(foundToken.getStatus() == WITHDRAWN) {
            throwIllegalActionException("Token is withdrawn");
        }
    }

    private void validateToken(SecretKey key, String token) {
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }


    @Override
    @Transactional(noRollbackFor = TokenIsUsedException.class)
    public RefreshTokenResponseDto refresh(String token) {
        var entity = tokenRepository.findByToken(token)
                .orElseThrow(() -> getEntityNotFoundException(TokenEntity.class));

        validateRefreshToken(entity.getToken());
        entity.setStatus(USED);
        tokenRepository.save(entity);

        return RefreshTokenResponseDto.builder()
                .accessToken(generateAccessToken(entity.getUser().getId()))
                .refreshToken(generateRefreshToken(entity.getUser().getId()))
                .build();
    }

    @Override
    public Claims getAccessClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretAccessKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
