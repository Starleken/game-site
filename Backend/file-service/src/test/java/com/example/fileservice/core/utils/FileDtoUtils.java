package com.example.fileservice.core.utils;

import com.example.fileservice.dto.FileUploadDto;

import static com.example.fileservice.core.utils.FileUtils.*;

public abstract class FileDtoUtils {

    public static FileUploadDto generateFileUploadDto(Class testClass) {
        return FileUploadDto.builder()
                .file(getMockMultipartFile(testClass))
                .build();
    }
}
