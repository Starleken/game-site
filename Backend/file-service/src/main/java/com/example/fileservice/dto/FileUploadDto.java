package com.example.fileservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class FileUploadDto {

    @NotNull(message = "the upload request must contains file")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private MultipartFile file;
}
