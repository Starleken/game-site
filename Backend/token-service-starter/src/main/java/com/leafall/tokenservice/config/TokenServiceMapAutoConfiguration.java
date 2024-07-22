package com.leafall.tokenservice.config;

import com.leafall.tokenservice.component.TokenFilter;
import com.leafall.tokenservice.service.AuthContextResolver;
import com.leafall.tokenservice.service.TokenService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TokenServiceMapProperties.class)
public class TokenServiceMapAutoConfiguration {

    @Bean
    public TokenService tokenService(TokenServiceMapProperties properties) {
        return new TokenService(properties.getSecretKey());
    }

    @Bean
    public AuthContextResolver authContextResolver(TokenServiceMapProperties properties) {
        return new AuthContextResolver(tokenService(properties));
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenFilter tokenFilter(TokenServiceMapProperties properties) {
        return new TokenFilter(authContextResolver(properties));
    }
}
