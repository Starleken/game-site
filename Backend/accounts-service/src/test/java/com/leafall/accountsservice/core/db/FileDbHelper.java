package com.leafall.accountsservice.core.db;

import com.leafall.accountsservice.core.utils.FileUtils;
import dto.FileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import service.FileService;

import java.util.UUID;

import static com.leafall.accountsservice.core.utils.FakerUtils.*;
import static com.leafall.accountsservice.utils.ExceptionUtils.*;

@Component
@RequiredArgsConstructor
public class FileDbHelper {

    private final FileService fileService;

    public FileResponseDto addFile(Class testClass) {
        return fileService.upload(FileUtils.getMockMultipartFile(testClass));
    }
}
