package com.leafall.accountsservice.controller;

import com.leafall.accountsservice.BaseIntegrationTest;
import com.leafall.accountsservice.core.db.GameDbHelper;
import com.leafall.accountsservice.core.db.ReviewDbHelper;
import com.leafall.accountsservice.dto.review.ReviewResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static com.leafall.accountsservice.core.utils.dto.ReviewDtoUtils.*;
import static com.leafall.accountsservice.core.utils.equals.ReviewEqualsUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReviewControllerTest extends BaseIntegrationTest {

    @Autowired
    private ReviewDbHelper reviewDbHelper;

    @Autowired
    private GameDbHelper gameDbHelper;

    @BeforeEach
    void setUp() {
        dbCleaner.clear();
    }

    @Test
    void testCreate_happyPath() throws Exception {
        //given
        var savedGame = gameDbHelper.saveGame(ReviewControllerTest.class);
        var createDto = generateCreateDto(savedGame.getId());

        //when
        var mvcResult = mockMvc.perform(post("/reviews")
                        .content(objectMapper.writeValueAsString(createDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, ReviewResponseDto.class);

        //then
        equal(response, createDto);
    }

    @Test
    void testCreate_whenGameNotFound_then404() throws Exception {
        //given
        var idToSearch = 5L;
        var createDto = generateCreateDto(idToSearch);

        //when
        mockMvc.perform(post("/reviews")
                        .content(objectMapper.writeValueAsString(createDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //then
    }

    @Test
    void testUpdate_happyPath() throws Exception {
        //given
        var savedGame = gameDbHelper.saveGame(ReviewControllerTest.class);
        var savedReview = reviewDbHelper.saveReview(savedGame);
        var updateDto = generateUpdateDto(savedReview.getId());

        //when
        var mvcResult = mockMvc.perform(put("/reviews")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, ReviewResponseDto.class);

        //then
        equal(response, updateDto);
    }

    @Test
    void testUpdate_whenReviewNotFound_then404() throws Exception {
        //given
        var idToSearch = 5L;
        var updateDto = generateUpdateDto(idToSearch);

        //when
        mockMvc.perform(put("/reviews")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //then
    }

    @Test
    void testDeleteById_happyPath() throws Exception {
        //given
        var savedGame = gameDbHelper.saveGame(ReviewControllerTest.class);
        var savedReview = reviewDbHelper.saveReview(savedGame);

        //when
        mockMvc.perform(delete("/reviews/{id}", savedReview.getId()))
                .andExpect(status().isOk());

        //then
    }

    @Test
    void testDeleteById_whenReviewNotFound_then404() throws Exception {
        //given
        var idToSearch = 5L;

        //when
        mockMvc.perform(delete("/reviews/{id}", idToSearch))
                .andExpect(status().isNotFound());

        //then
    }
}
