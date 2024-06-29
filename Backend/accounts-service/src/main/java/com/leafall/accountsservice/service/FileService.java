package com.leafall.accountsservice.service;

import com.leafall.accountsservice.dto.file.DownloadFileResponse;
import com.leafall.accountsservice.dto.file.FileResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {

    DownloadFileResponse download(String imagePath);

    FileResponseDto getById(UUID id);

    FileResponseDto upload(MultipartFile imageFile);
}
