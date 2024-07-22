package org.leafall.authservice.service;

public interface EncodingService {

    String encode(String password);
    boolean isMatch(String notEncoded, String encoded);
}
