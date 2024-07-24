package org.leafall.mainservice.core.db;

import lombok.RequiredArgsConstructor;
import org.leafall.fileservicestarter.dto.FileResponseDto;
import org.leafall.fileservicestarter.service.FileService;
import org.springframework.stereotype.Component;

import static org.leafall.mainservice.core.utils.FileUtils.*;

@Component
@RequiredArgsConstructor
public class FileDbHelper {

    private final FileService fileService;

    public FileResponseDto addFile(Class testClass) {
        return fileService.upload(getMockMultipartFile(testClass));
    }
}
