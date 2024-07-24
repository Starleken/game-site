package org.leafall.fileservicestarter.config;

import org.leafall.fileservicestarter.service.FileService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FileServiceMapProperties.class)
public class FileServiceMapAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public FileService fileService(FileServiceMapProperties properties) {
        return new FileService(properties.getFileServiceUrl());
    }
}
