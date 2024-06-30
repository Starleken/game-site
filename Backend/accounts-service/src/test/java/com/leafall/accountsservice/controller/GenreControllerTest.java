package com.leafall.accountsservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leafall.accountsservice.BaseIntegrationTest;
import com.leafall.accountsservice.core.db.GenreDbHelper;
import com.leafall.accountsservice.core.utils.dto.GenreDtoUtils;
import com.leafall.accountsservice.core.utils.equals.GenreEqualsUtils;
import com.leafall.accountsservice.dto.genre.GenreResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.leafall.accountsservice.core.utils.dto.GenreDtoUtils.*;
import static com.leafall.accountsservice.core.utils.equals.GenreEqualsUtils.*;
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
        var savedEntities = genreDbHelper.saveGenre(count);

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
        var createDto = generateCreateDto();

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
    void testUpdate_happyPath() throws Exception {
        //given
        var savedEntity = genreDbHelper.saveGenre();
        var updateDto = generateUpdateDto(savedEntity.getId());

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
    void testUpdate_whenNotFound_then404() throws Exception {
        //given
        var idToSearch = 5L;
        var updateDto = generateUpdateDto(idToSearch);

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
        var savedEntity = genreDbHelper.saveGenre();

        //when
        mockMvc.perform(delete("/genres/{id}", savedEntity.getId()))
                .andExpect(status().isOk());

        //then
        assertTrue(genreDbHelper.findById(savedEntity.getId()).isEmpty());
    }

    @Test
    void testDeleteById_whenNotFound_then404() throws Exception {
        //given
        var idToSearch = 5L;

        //when
        mockMvc.perform(delete("/genres/{id}", idToSearch))
                .andExpect(status().isNotFound());

        //then
    }
}
