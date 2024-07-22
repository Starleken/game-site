package org.leafall.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.leafall.authservice.service.EncodingService;
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
