package org.leafall.authservice.service;

import org.leafall.authservice.dto.token.RefreshTokenRequestDto;
import org.leafall.authservice.dto.token.RefreshTokenResponseDto;
import org.leafall.authservice.dto.user.*;

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
    void signup(UserSignupDto dto);
    LoginResponseDto login(LoginRequestDto loginDto);
    RefreshTokenResponseDto refresh(RefreshTokenRequestDto refreshDto);
}
