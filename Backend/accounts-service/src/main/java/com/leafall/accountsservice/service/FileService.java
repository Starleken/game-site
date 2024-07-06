package com.leafall.accountsservice.service;

import com.leafall.accountsservice.dto.file.DownloadFileResponse;
import com.leafall.accountsservice.dto.file.FileResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FileService {

    DownloadFileResponse download(String imagePath);

    FileResponseDto getById(UUID id);

    List<FileResponseDto> getAllByIds(List<UUID> ids);

    FileResponseDto upload(MultipartFile imageFile);

    List<FileResponseDto> uploadAll(List<MultipartFile> imageFiles);
}
