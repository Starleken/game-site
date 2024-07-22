package com.leafall.tokenservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public class AuthContext {

    private final Long userId;
    private final String role;
    private final String accessToken;
}
