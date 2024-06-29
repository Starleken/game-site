package com.leafall.accountsservice.service;

import com.leafall.accountsservice.BaseIntegrationTest;
import com.leafall.accountsservice.core.db.DbCleaner;
import com.leafall.accountsservice.core.db.FileDbHelper;
import com.leafall.accountsservice.core.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;

import static com.leafall.accountsservice.core.utils.FileUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FileServiceTest extends BaseIntegrationTest {

    @Autowired
    private FileDbHelper fileDbHelper;

    @Autowired
    private FileService fileService;

    @MockBean
    private S3Service s3Service;

    @BeforeEach
    void setUp() {
        dbCleaner.clear();
    }

    @Test
    void testUpload_happyPath() {
        //given
        var fileUrl = "s3://bucket/item/uui-1111";
        var originalFileName = "testFile.txt";
        when(s3Service.uploadFile(any(MultipartFile.class), any(), anyString())).thenReturn(fileUrl);
        var preSignedFileUrl = "s3://preSigned/uuid-1111";
        when(s3Service.generatePreSignedUrl(any(), any())).thenReturn(preSignedFileUrl);

        //when
        var result = fileService.upload(getMockMultipartFile(FileServiceTest.class));

        //then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(preSignedFileUrl, result.getFileUrl());

        var fileInDB = fileDbHelper.getById(result.getId());
        assertEquals(fileUrl, fileInDB.getFileUrl());
        assertEquals(originalFileName, fileInDB.getOriginalFileName());
    }

    @Test
    void testGetById_happyPath() throws Exception {
        //given
        var existingFile = fileDbHelper.addFile();

        var preSignedFileUrl = "s3://preSigned/uuid-1111";
        when(s3Service.generatePreSignedUrl(any(), any())).thenReturn(preSignedFileUrl);

        //when
        var result = fileService.getById(existingFile.getId());

        //then
        assertNotNull(result);
        assertEquals(existingFile.getId(), result.getId());
        assertEquals(existingFile.getOriginalFileName(), result.getOriginalFileName());
        assertEquals(preSignedFileUrl, result.getFileUrl());
    }
}
