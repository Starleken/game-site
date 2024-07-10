package com.example.fileservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    String uploadFile(MultipartFile file, String keyName, String bucketName);

    String generatePreSignedUrl(String fileUrl, String bucketName);
}
