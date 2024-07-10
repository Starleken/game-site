package com.example.fileservice.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    private static final String accessKeyId = "YCAJEKQNdqMcHTOZdRNUc0a1Q";
    private static final String secretAccessKey = "YCOWwNbgyxRKhOS29oMDicL8th665cnPJCRYIX47";

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                "https://storage.yandexcloud.net",
                                "ru-central1"
                        )
                )
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretAccessKey)))
                .build();
    }

    @Bean
    public TransferManager transferManager(AmazonS3 amazonS3) {
        return TransferManagerBuilder.standard()
                .withS3Client(amazonS3)
                .build();
    }
}
