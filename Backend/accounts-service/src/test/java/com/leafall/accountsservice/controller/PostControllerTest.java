package com.leafall.accountsservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leafall.accountsservice.BaseIntegrationTest;
import com.leafall.accountsservice.core.db.PostDbHelper;
import com.leafall.accountsservice.dto.post.PostResponseDto;
import com.leafall.accountsservice.dto.post.PostResponseShortDto;
import dto.FileResponseDto;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import service.FileService;
import service.HistoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.leafall.accountsservice.core.utils.FileUtils.*;
import static com.leafall.accountsservice.core.utils.dto.PostDtoUtils.*;
import static com.leafall.accountsservice.core.utils.equals.PostEqualsUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerTest extends BaseIntegrationTest {

    @Autowired
    private PostDbHelper postDbHelper;

    @MockBean
    private FileService fileService;

    @BeforeEach
    void setUp() {
        dbCleaner.clear();
        reset(historyService);

        mockFileService();
    }

    @Test
    void testFindAll_happyPath() throws Exception {
        //given
        int count = 3;
        postDbHelper.save(count, PostControllerTest.class);

        //when
        var mvcResult = mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var dtos = objectMapper.readValue(
                bytes, new TypeReference<List<PostResponseShortDto>>() {
                });

        //then
        assertEquals(count, dtos.size());
    }

    @Test
    void testFindById_happyPath() throws Exception {
        //given
        var commentsCount = 5;
        var savedPost = postDbHelper.saveWithComments(commentsCount);

        //when
        var mvcResult = mockMvc.perform(get("/posts/{id}", savedPost.getId()))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var dto = objectMapper.readValue(bytes, PostResponseDto.class);

        //then
        equal(savedPost, dto);
        assertEquals(commentsCount, dto.getComments().size());
        assertNotNull(dto.getImage());
    }

    @Test
    void testFindById_whenPostNotFound_then404() throws Exception {
        //given
        var idToSearch = 1L;

        //when
        mockMvc.perform(get("/posts/{id}", idToSearch))
                .andExpect(status().isNotFound());

        //then
    }

    @Test
    void testCreate_happyPath() throws Exception {
        //given
        var createDto = generateCreateDto();
        var file = getMockMultipartFile(PostControllerTest.class);

        //when
        var mvcResult = mockMvc.perform(multipart(POST,"/posts")
                        .file(file)
                        .contentType(MULTIPART_FORM_DATA)
                        .param("header", createDto.getHeader())
                        .param("content", createDto.getContent()))
                .andExpect(status().isCreated())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var dto = objectMapper.readValue(bytes, PostResponseDto.class);

        //then
        equal(createDto, dto);
        assertNotNull(dto.getImage());
    }

    @Test
    void testCreate_whenDtoIsNotValid_then400() throws Exception {
        //given
        var createDto = generateCreateDto();
        createDto.setContent("");
        var file = getMockMultipartFile(PostControllerTest.class);

        //when
        mockMvc.perform(multipart(POST,"/posts")
                        .file(file)
                        .contentType(MULTIPART_FORM_DATA)
                        .param("header", createDto.getHeader())
                        .param("content", createDto.getContent()))
                .andExpect(status().isBadRequest());

        //then
    }

    @Test
    void testCreate_whenCreate_thenHistoryServiceCalledOnce() throws Exception {
        //given
        var createDto = generateCreateDto();
        var file = getMockMultipartFile(PostControllerTest.class);

        //when
        mockMvc.perform(multipart(POST,"/posts")
                        .file(file)
                        .contentType(MULTIPART_FORM_DATA)
                        .param("header", createDto.getHeader())
                        .param("content", createDto.getContent()))
                .andExpect(status().isCreated());

        //then
        verify(historyService, times(1)).sendMessage(any(), any(), any());
    }

    @Test
    void testUpdate_happyPath() throws Exception {
        //given
        var savedPost = postDbHelper.save();
        var updateDto = generateUpdateDto(savedPost.getId());

        //when
        var mvcResult = mockMvc.perform(put("/posts")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var dto = objectMapper.readValue(bytes, PostResponseDto.class);

        //then
        equal(updateDto, dto);
        assertNotNull(dto.getImage());
    }

    @Test
    void testUpdate_whenDtoIsNotValid_then400() throws Exception {
        //given
        var idToGenerate = 1L;
        var updateDto = generateUpdateDto(idToGenerate);
        updateDto.setContent("");

        //when
        mockMvc.perform(put("/posts")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //then
    }

    @Test
    void testUpdate_whenPostNotFound_then404() throws Exception {
        //given
        var fakeId = 1L;
        var updateDto = generateUpdateDto(fakeId);

        //when
        mockMvc.perform(put("/posts")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //then
    }

    @Test
    void testDeleteById_happyPath() throws Exception {
        //given
        var savedPost = postDbHelper.save();

        //when
        mockMvc.perform(delete("/posts/{id}", savedPost.getId()))
                .andExpect(status().isOk());

        //then
        assertTrue(postDbHelper
                .findById(savedPost.getId()).isEmpty());
    }

    @Test
    void testDeleteById_whenPostNotFound_then404() throws Exception {
        //given
        var idToSearch = 1L;

        //when
        mockMvc.perform(delete("/posts/{id}", idToSearch))
                .andExpect(status().isNotFound());

        //then
    }

    @Test
    void testChangeImage_happyPath() throws Exception {
        //given
        var savedPost = postDbHelper.save();
        var changeImageDto = generateChangeImageDto(savedPost.getId());

        //when
        var result = mockMvc.perform(multipart(PUT, "/posts/image")
                        .file(getMockMultipartFile(PostControllerTest.class))
                        .contentType(MULTIPART_FORM_DATA)
                        .param("id", changeImageDto.getId().toString()))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = result.getResponse().getContentAsByteArray();
        var fileResponse = objectMapper.readValue(bytes, PostResponseDto.class);

        //then
        assertNotNull(fileResponse);
        assertNotNull(fileResponse.getImage());
    }

    private void mockFileService() {
        when(fileService.upload(any())).thenReturn(getFileResponseDto());

        when(fileService.findById(any())).thenReturn(getFileResponseDto());
    }
}
