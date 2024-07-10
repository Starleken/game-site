package com.example.fileservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DownloadFileRequestDto {

    private String url;
}
