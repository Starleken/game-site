package com.example.fileservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class FileUploadDto {

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private MultipartFile file;
}
