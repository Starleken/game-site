package com.example.fileservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class FileResponseDto {

    private UUID id;
    private String fileUrl;
    private String originalFileName;
}
