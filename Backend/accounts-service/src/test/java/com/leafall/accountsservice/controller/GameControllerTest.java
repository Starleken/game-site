package com.leafall.accountsservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leafall.accountsservice.BaseIntegrationTest;
import com.leafall.accountsservice.core.db.GameDbHelper;
import com.leafall.accountsservice.dto.game.GameResponseDto;
import com.leafall.accountsservice.dto.game.GameResponseShortDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static com.leafall.accountsservice.core.utils.FileUtils.getMockMultipartFile;
import static com.leafall.accountsservice.core.utils.FileUtils.getMockMultipartFiles;
import static com.leafall.accountsservice.core.utils.dto.GameDtoUtils.*;
import static com.leafall.accountsservice.core.utils.equals.GameEqualsUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GameControllerTest extends BaseIntegrationTest {

    @Autowired
    private GameDbHelper dbHelper;

    @BeforeEach
    void setUp() {
        dbCleaner.clear();
    }

    @Test
    void testFindAll_happyPath() throws Exception {
        //given
        var count = 4;
        var saved = dbHelper.saveGame(count, GameControllerTest.class);

        //when
        var mvcResult = mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, new TypeReference<List<GameResponseShortDto>>() {
        });

        //then
        assertEquals(count, response.size());
    }

    @Test
    void testFindById_happyPath() throws Exception {
        //given
        var saved = dbHelper.saveGame(GameControllerTest.class);

        //when
        var mvcResult = mockMvc.perform(get("/games/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, GameResponseDto.class);

        //then
        equal(saved, response);
    }

    @Test
    void testFindById_whenSaveWithReviews_thenReturnReviews() throws Exception {
        //given
        var reviewsCount = 4;
        var saved = dbHelper.saveGameWithReviews(reviewsCount, GameControllerTest.class);

        //when
        var mvcResult = mockMvc.perform(get("/games/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, GameResponseDto.class);

        //then
        assertNotNull(response.getRating());
        assertEquals(reviewsCount, response.getReviews().size());
        equal(saved, response);
    }

    @Test
    void testCreate_happyPath() throws Exception {
        //given
        int imagesCount = 5;
        int genreCount = 5;
        var savedGenresIds = dbHelper.saveGenres(genreCount);
        var createDto = generateCreateDto();
        createDto.setGenres(savedGenresIds);

        var multipartRequest = multipart(POST, "/games");
        multipartRequest.contentType(MULTIPART_FORM_DATA);

        for (MockMultipartFile file : getMockMultipartFiles(GameControllerTest.class, imagesCount)) {
            multipartRequest.file("images", file.getBytes());
        }
        multipartRequest.file("headerImage", getMockMultipartFile(GameControllerTest.class).getBytes());

        for (Long id : createDto.getGenres()) {
            multipartRequest.param("genres", id.toString());
        }
        multipartRequest.param("name", createDto.getName());
        multipartRequest.param("description", createDto.getDescription());

        //when
        var mvcResult = mockMvc.perform(multipartRequest)
                .andExpect(status().isCreated())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var dto = objectMapper.readValue(bytes, GameResponseDto.class);

        //then
        assertNotNull(dto.getHeaderImage());
        equal(createDto, dto);
    }

    @Test
    void testUpdate_happyPath() throws Exception {
        //given
        var saved = dbHelper.saveGame(GameControllerTest.class);
        var updateDto = generateUpdateDto(saved.getId());
        updateDto.setGenres(dbHelper.saveGenres(5));

        //when
        var mvcResult = mockMvc.perform(put("/games")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, GameResponseDto.class);

        //then
        assertNotNull(response.getHeaderImage());
        equal(updateDto, response);
    }

    @Test
    void testUpdate_whenNotFound_then404() throws Exception {
        //given
        var idToSearch = 5L;
        var updateDto = generateUpdateDto(idToSearch);

        //when
        mockMvc.perform(put("/games")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //then
    }

    @Test
    void testDeleteById_happyPath() throws Exception {
        //given
        var saved = dbHelper.saveGame(GameControllerTest.class);

        //when
        mockMvc.perform(delete("/games/{id}", saved.getId()))
                .andExpect(status().isOk());

        var found = dbHelper.findById(saved.getId());

        //then
        assertTrue(found.isEmpty());
    }

    @Test
    void testDeleteById_whenNotFound_then404() throws Exception {
        //given
        var idToSearch = 5L;

        //when
        mockMvc.perform(delete("/games/{id}", idToSearch))
                .andExpect(status().isNotFound());

        //then
    }
}
