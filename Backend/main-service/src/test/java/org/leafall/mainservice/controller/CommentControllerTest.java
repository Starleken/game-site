package org.leafall.mainservice.controller;

import org.leafall.mainservice.BaseIntegrationTest;
import org.leafall.mainservice.core.db.CommentDbHelper;
import org.leafall.mainservice.core.db.PostDbHelper;
import org.leafall.mainservice.core.utils.equals.CommentEqualsUtils;
import org.leafall.mainservice.dto.comment.CommentResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.leafall.mainservice.core.utils.dto.CommentDtoUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CommentControllerTest extends BaseIntegrationTest {

    @Autowired
    private CommentDbHelper commentDbHelper;

    @Autowired
    private PostDbHelper postDbHelper;

    @BeforeEach
    void setUp() {
        dbCleaner.clear();
    }

    @Test
    void testCreate_happyPath() throws Exception {
        //given
        var savedPost = postDbHelper.save();
        var createDto = generateCreateDto(savedPost.getId());

        //when
        var mvcResult = mockMvc.perform(post("/comments")
                        .content(objectMapper.writeValueAsString(createDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, CommentResponseDto.class);

        //then
        CommentEqualsUtils.equal(response, createDto);
    }

    @Test
    void testCreate_whenDtoIsNotValid_then400() throws Exception {
        //given
        var idToGenerate = 1L;
        var createDto = generateCreateDto(idToGenerate);
        createDto.setContent("");

        //when
        mockMvc.perform(post("/comments")
                        .content(objectMapper.writeValueAsString(createDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //then
    }

    @Test
    void testCreate_whenPostNotFound_then404() throws Exception {
        //given
        var fakePostId = 1L;
        var createDto = generateCreateDto(fakePostId);

        //when
        mockMvc.perform(post("/comments")
                        .content(objectMapper.writeValueAsString(createDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //then
    }

    @Test
    void testUpdate_happyPath() throws Exception {
        //given
        var savedPost = postDbHelper.save();
        var savedComment = commentDbHelper.save(savedPost);
        var updateDto = generateUpdateDto(savedComment.getId());

        //when
        var mvcResult = mockMvc.perform(put("/comments")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, CommentResponseDto.class);

        //then
        CommentEqualsUtils.equal(response, updateDto);
    }

    @Test
    void testUpdate_whenDtoIsNotValid_then400() throws Exception {
        //given
        var idToGenerate = 1L;
        var updateDto = generateUpdateDto(idToGenerate);
        updateDto.setContent("");

        //when
        mockMvc.perform(post("/comments")
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
        mockMvc.perform(put("/comments")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteById_happyPath() throws Exception {
        //given
        var savedPost = postDbHelper.save();
        var savedComment = commentDbHelper.save(savedPost);

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/comments/{id}", savedComment.getId()))
                .andExpect(status().isOk());

        //then
        assertTrue(commentDbHelper.findById(savedComment.getId()).isEmpty());
    }

    @Test
    void testDeleteById_whenPostNotFound_then404() throws Exception {
        //given
        var fakeId = 1L;

        //when
        mockMvc.perform(delete("/comments/{id}", fakeId))
                .andExpect(status().isNotFound());

        //then
    }
}
