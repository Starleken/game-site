package com.example.fileservice.core.db;

import com.example.fileservice.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbCleaner {

    private final FileRepository fileRepository;

    public void clear() {
        fileRepository.deleteAll();
    }
}
