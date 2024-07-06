package com.leafall.accountsservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leafall.accountsservice.BaseIntegrationTest;
import com.leafall.accountsservice.core.db.UserDbHelper;
import com.leafall.accountsservice.core.utils.FakerUtils;
import com.leafall.accountsservice.core.utils.dto.UserDtoUtils;
import com.leafall.accountsservice.core.utils.equals.UserEqualsUtils;
import com.leafall.accountsservice.dto.user.UserCreateDto;
import com.leafall.accountsservice.dto.user.UserResponseDto;
import com.leafall.accountsservice.dto.user.UserResponseShortDto;
import com.leafall.accountsservice.entity.UserEntity;
import com.leafall.accountsservice.mapper.UserMapper;
import com.leafall.accountsservice.service.EncodingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static com.leafall.accountsservice.core.utils.FakerUtils.*;
import static com.leafall.accountsservice.core.utils.dto.UserDtoUtils.*;
import static com.leafall.accountsservice.core.utils.equals.UserEqualsUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseIntegrationTest {

    @Autowired
    private UserDbHelper userDbHelper;

    @Autowired
    private EncodingService encodingService;

    @BeforeEach
    void setUp() {
        dbCleaner.clear();
    }

    @Test
    void testFindAll_happyPath() throws Exception {
        //given
        var usersCount = 3;
        userDbHelper.saveUsers(usersCount);

        //when
        var mvcResult = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(
                bytes, new TypeReference<List<UserResponseShortDto>>() {
                });

        //then
        assertEquals(usersCount, response.size());

    }

    @Test
    void testFindById_happyPath() throws Exception {
        //given
        var savedUser = userDbHelper.saveUser();

        //when
        var mvcResult = mockMvc.perform(get("/users/{id}", savedUser.getId()))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, UserResponseDto.class);

        //then
        equal(savedUser, response);
    }

    @Test
    void testFindById_whenUserNotFound_then404() throws Exception {
        //given
        var idToSearch = 4;

        //when
        mockMvc.perform(get("/users/{id}", idToSearch))
                .andExpect(status().isNotFound());

        //then
    }

    @Test
    void testFindAllById_happyPath() throws Exception {
        //given
        var entities = userDbHelper.saveUsers(3);
        var ids = entities.stream().map(UserEntity::getId).toList();

        //when
        var mvcResult = mockMvc.perform(get("/users/ids")
                        .content(objectMapper.writeValueAsString(ids))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(
                bytes, new TypeReference<List<UserResponseShortDto>>() {
                });

        //then
        assertEquals(ids.size(), response.size());
    }

    @Test
    void testCreate_happyPath() throws Exception {
        //given
        var createDto = generateCreateDto();

        //when
        var mvcResult = mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(createDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, UserResponseDto.class);

        //then
        equal(createDto, response);
    }

    @Test
    void testCreate_whenCreate_thenPasswordIsEncoded() throws Exception {
        //given
        var createDto = generateCreateDto();

        //when
        var mvcResult = mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(createDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, UserResponseDto.class);

        var saved = userDbHelper.findById(response.getId());

        //then
        assertNotEquals(createDto.getPassword() ,saved.getPassword());
    }

    @Test
    void testCreate_whenEmailExists_then400() throws Exception {
        //given
        var existedEmail = "starleken@Mail.ru";

        userDbHelper.saveUser(faker.name().username(), existedEmail);
        var createDto = generateCreateDto(faker.name().username(), existedEmail);

        //when
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(createDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        //then
    }

    @Test
    void testCreate_whenUsernameExists_then400() throws Exception {
        //given
        var existedUsername = "starleken";

        userDbHelper.saveUser(existedUsername, faker.internet().emailAddress());
        var createDto = generateCreateDto(existedUsername, faker.internet().emailAddress());

        //when
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(createDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        //then
    }

    @Test
    void testUpdate_happyPath() throws Exception {
        //given
        var savedUser = userDbHelper.saveUser();
        var updateDto = generateUpdateDto(savedUser);

        //when
        var mvcResult = mockMvc.perform(put("/users")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, UserResponseDto.class);

        //then
        equal(updateDto, response);
    }

    @Test
    void testUpdate_whenUserNotFound_then404() throws Exception {
        //given
        var idToSearch = 5L;
        var updateDto = generateUpdateDto(idToSearch);

        //when
        var mvcResult = mockMvc.perform(put("/users")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        //then
    }

    @Test
    void testUpdate_whenUsernameExists_then400() throws Exception {
        //given
        var savedUser = userDbHelper.saveUser();
        var updateDto = generateUpdateDto(savedUser, savedUser.getUsername());

        //when
        var mvcResult = mockMvc.perform(put("/users")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        //then
    }

    @Test
    void testDeleteById_happyPath() throws Exception {
        //given
        var savedUser = userDbHelper.saveUser();

        //when
        mockMvc.perform(delete("/users/{id}", savedUser.getId()))
                .andExpect(status().isOk());

        //then
    }

    @Test
    void testDeleteById_whenUserNotFound_then404() throws Exception {
        //given
        var idToSearch = 5L;

        //when
        mockMvc.perform(delete("/users/{id}", idToSearch))
                .andExpect(status().isNotFound());

        //then
    }

    @Test
    void testChangePassword_happyPath() throws Exception {
        //given
        var oldPassword = "oldPassword";
        var savedUser = userDbHelper.saveUser(oldPassword);
        var changeDto = generatePasswordDto(savedUser, oldPassword);

        //when
        mockMvc.perform(put("/users/password")
                        .content(objectMapper.writeValueAsString(changeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var updated = userDbHelper.findById(savedUser.getId());

        //then
        assertTrue(encodingService.isMatch(changeDto.getNewPassword(), updated.getPassword()));
    }

    @Test
    void testChangePassword_whenUserNotFound_then404() throws Exception {
        //given
        var oldPassword = "oldPassword";
        var idToSearch = 5L;
        var changeDto = generatePasswordDto(idToSearch, oldPassword);

        //when
        mockMvc.perform(put("/users/password")
                        .content(objectMapper.writeValueAsString(changeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //then
    }

    @Test
    void testChangePassword_whenPasswordsNotEquals_then400() throws Exception {
        //given
        var oldPassword = "oldPassword";
        var savedUser = userDbHelper.saveUser(oldPassword);
        var changeDto = generatePasswordDto(savedUser, "Fake " + oldPassword);

        //when
        mockMvc.perform(put("/users/password")
                        .content(objectMapper.writeValueAsString(changeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //then
    }

    @Test
    void testChangeEmail_happyPath() throws Exception {
        //given
        var newEmail = "starleken@mail.ru";
        var savedUser = userDbHelper.saveUser();
        var changeDto = generateEmailDto(savedUser, newEmail);

        //when
        mockMvc.perform(put("/users/email")
                        .content(objectMapper.writeValueAsString(changeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var updated = userDbHelper.findById(savedUser.getId());

        //then
        assertEquals(changeDto.getNewEmail(), updated.getEmail());
    }

    @Test
    void testChangeEmail_whenUserNotFound_then404() throws Exception {
        //given
        var newEmail = "starleken@mail.ru";
        var idToSearch = 5L;
        var changeDto = generateEmailDto(idToSearch, newEmail);

        //when
        mockMvc.perform(put("/users/email")
                        .content(objectMapper.writeValueAsString(changeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //then
    }

    @Test
    void testChangeEmail_whenEmailExists_then400() throws Exception {
        //given
        var savedUser = userDbHelper.saveUser();
        var userToUpdate = userDbHelper.saveUser();
        var changeDto = generateEmailDto(userToUpdate, savedUser.getEmail());

        //when
        mockMvc.perform(put("/users/email")
                        .content(objectMapper.writeValueAsString(changeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //then
    }
}
