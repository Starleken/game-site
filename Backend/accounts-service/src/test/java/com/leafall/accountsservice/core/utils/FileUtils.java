package com.leafall.accountsservice.core.utils;

import dto.FileResponseDto;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUtils {

    @SneakyThrows
    public static MockMultipartFile getMockMultipartFile(Class testClass) {
        try (var is = testClass.getResourceAsStream("/testFile.txt")) {
            var fileBytes = is.readAllBytes();
            return new MockMultipartFile("file", "testFile.txt", MediaType.MULTIPART_FORM_DATA_VALUE, fileBytes);
        }
    }

    @SneakyThrows
    public static List<MockMultipartFile> getMockMultipartFiles(Class testClass, int count) {
        List<MockMultipartFile> files = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            files.add(getMockMultipartFile(testClass));
        }

        return files;
    }

    public static FileResponseDto getFileResponseDto() {
        var fileResponseDto = new FileResponseDto();
        fileResponseDto.setId(UUID.randomUUID());
        fileResponseDto.setFileUrl("s3://bucket/item/uui-1111");
        fileResponseDto.setOriginalFileName("testFile.txt");

        return fileResponseDto;
    }

    public static FileResponseDto getFileResponseDto(UUID uuid) {
        var fileResponseDto = getFileResponseDto();
        fileResponseDto.setId(uuid);

        return fileResponseDto;
    }
}
