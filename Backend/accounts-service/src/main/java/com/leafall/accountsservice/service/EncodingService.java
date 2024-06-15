package com.leafall.accountsservice.service;

public interface EncodingService {

    String encode(String password);
    boolean isMatch(String notEncoded, String encoded);
}
