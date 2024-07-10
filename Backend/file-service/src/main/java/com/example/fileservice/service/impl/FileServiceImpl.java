package com.example.fileservice.service.impl;

import com.example.fileservice.dto.*;
import com.example.fileservice.entity.FileEntity;
import com.example.fileservice.mapper.FileMapper;
import com.example.fileservice.repository.FileRepository;
import com.example.fileservice.service.FileService;
import com.example.fileservice.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.fileservice.utils.ExceptionUtils.*;
import static java.lang.String.format;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final S3Service s3Service;
    private final FileMapper fileMapper;

    @Value("${BUCKET_NAME}")
    private String bucketName;

    @SneakyThrows
    @Override
    public DownloadFileResponseDto download(DownloadFileRequestDto requestDto) {
        var httpClient = HttpClient.newBuilder().build();

        var httpRequest = HttpRequest
                .newBuilder()
                .uri(new URI(requestDto.getUrl()))
                .header("Cookie", "PHPSESSID=41ef67f165bcc020d89a0")
                .GET()
                .build();

        var response = httpClient
                .send(httpRequest, responseInfo -> HttpResponse.BodySubscribers.ofInputStream());

        var contentDisposition = response.headers().firstValue("Content-Disposition").orElseThrow();
        var fileName = contentDisposition.split("filename=")[1].replaceAll("\"", "");

        return DownloadFileResponseDto.builder()
                .in(response.body())
                .originalFileName(fileName)
                .build();
    }

    @Override
    public FileResponseDto getById(UUID id) {
        var foundFile = fileRepository.findById(id)
                .orElseThrow(() -> getNotFoundException(FileEntity.class));

        var preSignedUrl = s3Service.generatePreSignedUrl(foundFile.getFileUrl(), bucketName);

        return fileMapper.mapToDto(foundFile.getId(), preSignedUrl, foundFile.getOriginalFileName());
    }

    @Override
    public List<FileResponseDto> getAllByIds(List<UUID> ids) {
        var foundFiles = fileRepository.findAllById(ids);

        List<FileResponseDto> dtos = new ArrayList<>();
        for(var file : foundFiles) {
            var preSignedUrl = s3Service.generatePreSignedUrl(file.getFileUrl(), bucketName);
            var dto = fileMapper.mapToDto(file.getId(), preSignedUrl, file.getOriginalFileName());
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public FileResponseDto upload(FileUploadDto uploadDto) {
        var keyName = format("files/%s", randomUUID());
        var originalFileName = uploadDto.getFile().getOriginalFilename();
        var fileUrl = s3Service.uploadFile(uploadDto.getFile(), keyName, bucketName);

        var entityToSave = fileMapper.map(fileUrl, originalFileName);
        var savedEntity = fileRepository.save(entityToSave);

        var preSigneUrl = s3Service.generatePreSignedUrl(savedEntity.getFileUrl(), bucketName);
        return fileMapper.mapToDto(savedEntity.getId(), preSigneUrl, savedEntity.getOriginalFileName());
    }

    @Override
    public List<FileResponseDto> uploadAll(FileUploadDtoWrapper imageFiles) {
        List<FileResponseDto> dtos = new ArrayList<>();
        for (var file : imageFiles.getFiles()) {
            var uploadDto = FileUploadDto.builder().file(file).build();
            dtos.add(upload(uploadDto));
        }

        return dtos;
    }
}
