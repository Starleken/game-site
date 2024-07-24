package org.leafall.tokenservicestarter.service;

import org.leafall.tokenservicestarter.model.AuthContext;
import org.springframework.util.StringUtils;

public class AuthContextResolver {

    private final TokenService tokenService;

    public AuthContextResolver(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public AuthContext getContextFromToken(String token) {
        var accessToken = excludeToken(token);

        if (accessToken != null) {
            tokenService.validateToken(accessToken);
            var claims = tokenService.getClaims(accessToken);
            var role = claims.get("role", String.class);
            return new AuthContext(Long.valueOf(claims.getSubject()), role, accessToken);
        }

        return null;
    }

    private String excludeToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return null;
    }
}
