package org.leafall.mainservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.leafall.mainservice.BaseIntegrationTest;
import org.leafall.mainservice.core.db.GenreDbHelper;
import org.leafall.mainservice.dto.genre.GenreResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.leafall.mainservice.core.utils.dto.GenreDtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.leafall.mainservice.core.utils.equals.GenreEqualsUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GenreControllerTest extends BaseIntegrationTest {

    @Autowired
    private GenreDbHelper genreDbHelper;

    @BeforeEach
    void setUp() {
        dbCleaner.clear();
    }

    @Test
    void testFindAll_happyPath() throws Exception {
        //given
        var count = 5;
        genreDbHelper.saveGenre(count);

        //when
        var mvcResult = mockMvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, new TypeReference<List<GenreResponseDto>>() {
        });

        //then
        assertEquals(count, response.size());
    }

    @Test
    void testCreate_happyPath() throws Exception {
        //given
        var createDto = GenreDtoUtils.generateCreateDto();

        //when
        var mvcResult = mockMvc.perform(post("/genres")
                        .content(objectMapper.writeValueAsString(createDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, GenreResponseDto.class);

        //then
        equal(response, createDto);
    }

    @Test
    void testCreate_whenDtoIsNotValid_then400() throws Exception {
        //given
        var createDto = GenreDtoUtils.generateCreateDto();
        createDto.setName("");

        //when
        mockMvc.perform(post("/genres")
                        .content(objectMapper.writeValueAsString(createDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //then
    }

    @Test
    void testUpdate_happyPath() throws Exception {
        //given
        var savedEntity = genreDbHelper.saveGenre();
        var updateDto = GenreDtoUtils.generateUpdateDto(savedEntity.getId());

        //when
        var mvcResult = mockMvc.perform(put("/genres")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, GenreResponseDto.class);

        //then
        equal(response, updateDto);
    }

    @Test
    void testUpdate_whenDtoIsNotValid_then400() throws Exception {
        //given
        var idToGenerate = 1L;
        var updateDto = GenreDtoUtils.generateUpdateDto(idToGenerate);
        updateDto.setName("");

        //when
        mockMvc.perform(put("/genres")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //then
    }

    @Test
    void testUpdate_whenGenreNotFound_then404() throws Exception {
        //given
        var idToSearch = 5L;
        var updateDto = GenreDtoUtils.generateUpdateDto(idToSearch);

        //when
        mockMvc.perform(put("/genres")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //then
    }

    @Test
    void testDeleteById_happyPath() throws Exception {
        //given
        var savedGenre = genreDbHelper.saveGenre();

        //when
        mockMvc.perform(delete("/genres/{id}", savedGenre.getId()))
                .andExpect(status().isOk());

        //then
        assertTrue(genreDbHelper.findById(savedGenre.getId()).isEmpty());
    }

    @Test
    void testDeleteById_whenGenreNotFound_then404() throws Exception {
        //given
        var idToSearch = 5L;

        //when
        mockMvc.perform(delete("/genres/{id}", idToSearch))
                .andExpect(status().isNotFound());

        //then
    }
}
