package com.leafall.accountsservice.service;

import com.leafall.accountsservice.dto.user.*;

import java.util.List;

public interface UserService {

    List<UserResponseShortDto> findAll();
    UserResponseDto findById(long id);
    List<UserResponseShortDto> findAllById(List<Long> ids);
    UserResponseDto create(UserCreateDto createDto);
    UserResponseDto update(UserUpdateDto updateDto);
    void deleteById(long id);
    void changePassword(ChangePasswordDto passwordDto);
    void changeEmail(ChangeEmailDto emailDto);
}
