package org.leafall.authservice.service;

import io.jsonwebtoken.Claims;
import org.leafall.authservice.dto.token.RefreshTokenResponseDto;

public interface TokenService {

    String generateAccessToken(long userId);
    String generateRefreshToken(long userId);
    void validateAccessToken(String token);
    void validateRefreshToken(String token);
    RefreshTokenResponseDto refresh(String token);
    Claims getAccessClaims(String token);
}
