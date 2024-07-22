package com.leafall.tokenservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.beans.ConstructorProperties;

@ConfigurationProperties(prefix = "token-service-starter")
public class TokenServiceMapProperties {

    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
