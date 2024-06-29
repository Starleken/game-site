package com.leafall.accountsservice.core.db;

import com.leafall.accountsservice.core.utils.FakerUtils;
import com.leafall.accountsservice.entity.FileEntity;
import com.leafall.accountsservice.repository.FileRepository;
import com.leafall.accountsservice.utils.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.leafall.accountsservice.core.utils.FakerUtils.*;
import static com.leafall.accountsservice.utils.ExceptionUtils.*;

@Component
@RequiredArgsConstructor
public class FileDbHelper {

    private final FileRepository fileRepository;

    public FileEntity addFile() {
        var fileToSave = new FileEntity();
        fileToSave.setFileUrl(faker.internet().image());
        fileToSave.setOriginalFileName(faker.file().fileName());

        return fileRepository.saveAndFlush(fileToSave);
    }

    public FileEntity getById(UUID id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> getEntityNotFoundException(FileEntity.class));
    }
}
