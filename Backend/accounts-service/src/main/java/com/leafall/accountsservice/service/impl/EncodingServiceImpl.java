package com.leafall.accountsservice.service.impl;

import com.leafall.accountsservice.service.EncodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncodingServiceImpl implements EncodingService {

    private final PasswordEncoder encoder;

    @Override
    public String encode(String password) {
        return encoder.encode(password);
    }

    @Override
    public boolean isMatch(String notEncoded, String encoded) {
        return encoder.matches(notEncoded, encoded);
    }
}
