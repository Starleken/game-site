package org.leafall.mainservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.leafall.fileservicestarter.dto.FileResponseDto;
import org.leafall.fileservicestarter.service.FileService;
import org.leafall.mainservice.BaseIntegrationTest;
import org.leafall.mainservice.RestPage;
import org.leafall.mainservice.core.db.GameDbHelper;
import org.leafall.mainservice.dto.game.GameResponseDto;
import org.leafall.mainservice.dto.game.GameResponseShortDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.leafall.mainservice.core.utils.FileUtils.*;
import static org.leafall.mainservice.core.utils.FileUtils.getFileResponseDto;
import static org.leafall.mainservice.core.utils.dto.GameDtoUtils.*;
import static org.leafall.mainservice.core.utils.equals.GameEqualsUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GameControllerTest extends BaseIntegrationTest {

    @Autowired
    private GameDbHelper dbHelper;

    @MockBean
    private FileService fileService;

    @BeforeEach
    void setUp() {
        dbCleaner.clear();
        mockFileService();
    }

    @Test
    void testFindAll_happyPath() throws Exception {
        //given
        var count = 4;
        dbHelper.saveGame(4);

        //when
        var mvcResult = mockMvc.perform(get("/games?page=1&size=" + count))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, new TypeReference<RestPage<GameResponseShortDto>>() {
        });

        //then
        assertEquals(count, response.getTotalElements());
    }

    @Test
    void testFindById_happyPath() throws Exception {
        //given
        var saved = dbHelper.saveGame();

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
        var saved = dbHelper.saveGameWithReviews(reviewsCount);

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
    void testCreate_whenDtoIsNotValid_then400() throws Exception {
        //given
        var createDto = generateCreateDto();

        //when
        mockMvc.perform(multipart(POST, "/games")
                        .content(objectMapper.writeValueAsString(createDto))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());

        //then
    }

    @Test
    void testUpdate_happyPath() throws Exception {
        //given
        var saved = dbHelper.saveGame();
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
    void testUpdate_whenDtoIsNotValid_then400() throws Exception {
        //given
        var idToGenerate = 1L;
        var updateDto = generateUpdateDto(idToGenerate);
        updateDto.setDescription("");

        //when
        mockMvc.perform(put("/games")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //then
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
        var saved = dbHelper.saveGame();

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

    private void mockFileService() {
        when(fileService.upload(any())).thenReturn(getFileResponseDto());
        when(fileService.uploadAll(anyList())).thenAnswer(new Answer<List<FileResponseDto>>() {
            @Override
            public List<FileResponseDto> answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<?> files = invocationOnMock.getArgument(0);
                List<FileResponseDto> dtos = new ArrayList<>();
                for (int i = 0; i < files.size(); i++) {
                    dtos.add(getFileResponseDto());
                }

                return dtos;
            }
        });

        when(fileService.findById(any())).thenAnswer(new Answer<FileResponseDto>() {
            @Override
            public FileResponseDto answer(InvocationOnMock invocationOnMock) throws Throwable {
                return getFileResponseDto(invocationOnMock.getArgument(0));
            }
        });
        when(fileService.findAllByIds(anyList())).thenAnswer(new Answer<List<FileResponseDto>>() {
            @Override
            public List<FileResponseDto> answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<UUID> files = invocationOnMock.getArgument(0);
                List<FileResponseDto> dtos = new ArrayList<>();
                for (var uuid : files) {
                    dtos.add(getFileResponseDto(uuid));
                }

                return dtos;
            }
        });
    }
}
