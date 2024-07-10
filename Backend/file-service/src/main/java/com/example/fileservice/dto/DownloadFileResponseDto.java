package com.example.fileservice.dto;

import lombok.Builder;
import lombok.Data;

import java.io.InputStream;

@Data
@Builder
public class DownloadFileResponseDto {

    private InputStream in;
    private String originalFileName;
}
