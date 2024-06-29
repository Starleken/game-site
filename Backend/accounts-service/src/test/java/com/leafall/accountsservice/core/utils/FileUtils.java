package com.leafall.accountsservice.core.utils;

import com.leafall.accountsservice.service.FileServiceTest;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public class FileUtils {

    @SneakyThrows
    public static MockMultipartFile getMockMultipartFile(Class testClass) {
        try (var is = testClass.getResourceAsStream("/testFile.txt")) {
            var fileBytes = is.readAllBytes();
            return new MockMultipartFile("file", "testFile.txt", MediaType.MULTIPART_FORM_DATA_VALUE, fileBytes);
        }
    }
}
