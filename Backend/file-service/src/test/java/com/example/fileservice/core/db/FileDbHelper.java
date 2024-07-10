package com.example.fileservice.core.db;

import com.example.fileservice.entity.FileEntity;
import com.example.fileservice.repository.FileRepository;
import com.example.fileservice.utils.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.fileservice.core.utils.FakerUtils.faker;
import static com.example.fileservice.utils.ExceptionUtils.*;

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

    public List<FileEntity> addFile(int count) {
        List<FileEntity> files = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            var fileToSave = new FileEntity();
            fileToSave.setFileUrl(faker.internet().image());
            fileToSave.setOriginalFileName(faker.file().fileName());
            files.add(fileToSave);
        }

        return fileRepository.saveAllAndFlush(files);
    }

    public FileEntity getById(UUID id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> getNotFoundException(FileEntity.class));
    }
}
