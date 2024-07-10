package com.example.fileservice.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.example.fileservice.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import static com.amazonaws.HttpMethod.GET;


@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private static final String S3_PREFIX = "s3://";

    private final AmazonS3 amazonS3;
    private final TransferManager transferManager;
    @Value("${aws.s3.presigned-url-ttl-millis}")
    private Long preSignedUrlTtl;

    @SneakyThrows
    @Override
    public String uploadFile(MultipartFile file, String keyName, String bucketName) {
        var metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        var upload = transferManager.upload(bucketName, keyName, file.getInputStream(), metadata);

        try {
            upload.waitForCompletion();
        } catch (AmazonClientException ex) {
            throw ex;
        }

        return String.format("%s%s/%s", S3_PREFIX, bucketName, keyName);
    }

    @Override
    public String generatePreSignedUrl(String fileUrl, String bucketName) {
        String url;

        try {
            var expiration = new Date();
            var expTimeMillis = expiration.getTime();
            expTimeMillis += preSignedUrlTtl;
            expiration.setTime(expTimeMillis);

            var trimmedKey = fileUrl.replace(S3_PREFIX + bucketName + "/", "");

            var generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, trimmedKey)
                    .withMethod(GET)
                    .withExpiration(expiration);
            url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
        } catch (Exception ex) {
            url = "Error";
        }

        return url;
    }
}
