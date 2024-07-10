package com.example.fileservice.service;

import com.example.fileservice.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FileService {

    DownloadFileResponseDto download(DownloadFileRequestDto requestDto);

    FileResponseDto getById(UUID id);

    List<FileResponseDto> getAllByIds(List<UUID> ids);

    FileResponseDto upload(FileUploadDto imageFile);

    List<FileResponseDto> uploadAll(FileUploadDtoWrapper imageFiles);
}
