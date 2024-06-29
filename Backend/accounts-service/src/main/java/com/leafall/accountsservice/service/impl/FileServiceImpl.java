package com.leafall.accountsservice.service.impl;

import com.leafall.accountsservice.dto.file.DownloadFileResponse;
import com.leafall.accountsservice.dto.file.FileResponseDto;
import com.leafall.accountsservice.entity.FileEntity;
import com.leafall.accountsservice.mapper.FileMapper;
import com.leafall.accountsservice.repository.FileRepository;
import com.leafall.accountsservice.service.FileService;
import com.leafall.accountsservice.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static com.leafall.accountsservice.utils.ExceptionUtils.*;
import static java.lang.String.*;
import static java.util.UUID.*;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    //TODO create microservice

    private final FileRepository fileRepository;
    private final S3Service s3Service;
    private final FileMapper fileMapper;

    @Value("${BUCKET_NAME}")
    private String bucketName;

    @SneakyThrows
    @Override
    public DownloadFileResponse download(String url) {
        var httpClient = HttpClient.newBuilder().build();

        var httpRequest = HttpRequest
                .newBuilder()
                .uri(new URI(url))
                .header("Cookie", "PHPSESSID=41ef67f165bcc020d89a0")
                .GET()
                .build();

        var response = httpClient
                .send(httpRequest, responseInfo -> HttpResponse.BodySubscribers.ofInputStream());

        var contentDisposition = response.headers().firstValue("Content-Disposition").orElseThrow();
        var fileName = contentDisposition.split("filename=")[1].replaceAll("\"", "");

        return DownloadFileResponse.builder()
                .in(response.body())
                .originalFileName(fileName)
                .build();
    }

    @Override
    public FileResponseDto getById(UUID id) {
        var foundFile = fileRepository.findById(id)
                .orElseThrow(() -> getEntityNotFoundException(FileEntity.class));

        var preSignedUrl = s3Service.generatePreSignedUrl(foundFile.getFileUrl(), bucketName);

        return fileMapper.mapToDto(foundFile.getId(), preSignedUrl, foundFile.getOriginalFileName());
    }

    @Override
    public FileResponseDto upload(MultipartFile file) {
        var keyName = format("files/%s", randomUUID());
        var originalFileName = file.getOriginalFilename();
        var fileUrl = s3Service.uploadFile(file, keyName, bucketName);

        var entityToSave = fileMapper.map(fileUrl, originalFileName);
        var savedEntity = fileRepository.save(entityToSave);

        var preSigneUrl = s3Service.generatePreSignedUrl(savedEntity.getFileUrl(), bucketName);
        return fileMapper.mapToDto(savedEntity.getId(), preSigneUrl, savedEntity.getOriginalFileName());
    }
}
