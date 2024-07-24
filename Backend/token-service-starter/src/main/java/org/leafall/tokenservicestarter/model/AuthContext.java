package org.leafall.tokenservicestarter.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthContext {

    private final Long userId;
    private final String role;
    private final String accessToken;
}
