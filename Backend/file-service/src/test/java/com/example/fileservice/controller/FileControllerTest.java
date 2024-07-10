package com.example.fileservice.controller;

import com.example.fileservice.BaseIntegrationTest;
import com.example.fileservice.core.utils.FileDtoUtils;
import com.example.fileservice.core.utils.FileUtils;
import com.example.fileservice.dto.FileResponseDto;
import com.example.fileservice.dto.FileUploadDto;
import com.example.fileservice.dto.FileUploadDtoWrapper;
import com.example.fileservice.entity.FileEntity;
import com.example.fileservice.service.S3Service;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.example.fileservice.core.utils.FileDtoUtils.*;
import static com.example.fileservice.core.utils.FileUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FileControllerTest extends BaseIntegrationTest {

    @MockBean
    private S3Service s3Service;

    @Test
    void testUpload_happyPath() throws Exception {
        //given
        mockS3Service();
        var originalFileName = "testFile.txt";

        //when
        var mvcResult = mockMvc.perform(multipart(POST,"/files/upload")
                        .file(getMockMultipartFile(FileControllerTest.class))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, FileResponseDto.class);

        //then
        assertEquals(originalFileName, response.getOriginalFileName());
    }

    @Test
    void testUploadAll_happyPath() throws Exception {
        //given
        var filesCount = 2;
        mockS3Service();

        //when
        var mvcResult = mockMvc.perform(multipart("/files/upload/all")
                        .file("files", getMockMultipartFile(FileControllerTest.class).getBytes())
                        .file("files", getMockMultipartFile(FileControllerTest.class).getBytes())
                        .contentType(MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, new TypeReference<List<FileResponseDto>>() {
        });

        //then
        assertEquals(filesCount, response.size());
    }

    @Test
    void testGetById_happyPath() throws Exception {
        //given
        var existingFile = fileDbHelper.addFile();

        var preSignedFileUrl = "s3://preSigned/uuid-1111";
        when(s3Service.generatePreSignedUrl(any(), any())).thenReturn(preSignedFileUrl);

        //when
        var mvcResult = mockMvc.perform(get("/files/{id}",  existingFile.getId()))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, FileResponseDto.class);

        //then
        assertNotNull(response);
        assertEquals(existingFile.getId(), response.getId());
        assertEquals(existingFile.getOriginalFileName(), response.getOriginalFileName());
        assertEquals(preSignedFileUrl, response.getFileUrl());
    }

    @Test
    void testGetAllByIds_happyPath() throws Exception {
        //given
        var filesCount = 5;
        var savedFiles = fileDbHelper.addFile(filesCount);

        var request = get("/files/ids");
        for (var file : savedFiles) {
            request.param("id", file.getId().toString());
        }

        //when
        var mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, new TypeReference<List<FileResponseDto>>() {
        });

        //then
        assertEquals(filesCount, response.size());
    }

    private void mockS3Service() {
        var fileUrl = "s3://bucket/item/uui-1111";
        when(s3Service.uploadFile(any(MultipartFile.class), any(), anyString())).thenReturn(fileUrl);
        var preSignedFileUrl = "s3://preSigned/uuid-1111";
        when(s3Service.generatePreSignedUrl(any(), any())).thenReturn(preSignedFileUrl);
    }
}
