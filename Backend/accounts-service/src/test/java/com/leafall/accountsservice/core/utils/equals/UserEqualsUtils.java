package com.leafall.accountsservice.core.utils.equals;

import com.leafall.accountsservice.dto.user.UserCreateDto;
import com.leafall.accountsservice.dto.user.UserResponseDto;
import com.leafall.accountsservice.dto.user.UserUpdateDto;
import com.leafall.accountsservice.entity.UserEntity;
import org.junit.jupiter.api.Assertions;

public abstract class UserEqualsUtils {

    public static void equal(UserEntity entity, UserResponseDto dto) {
        Assertions.assertEquals(entity.getId(), dto.getId());
        Assertions.assertEquals(entity.getEmail(), dto.getEmail());
        Assertions.assertEquals(entity.getUsername(), dto.getUsername());
        Assertions.assertEquals(entity.getRole(), dto.getRole());
    }

    public static void equal(UserCreateDto createDto, UserResponseDto responseDto) {
        Assertions.assertEquals(createDto.getEmail(), responseDto.getEmail());
        Assertions.assertEquals(createDto.getUsername(), responseDto.getUsername());
        Assertions.assertEquals(createDto.getRole(), responseDto.getRole());
    }

    public static void equal(UserUpdateDto updateDto, UserResponseDto responseDto) {
        Assertions.assertEquals(updateDto.getId(), responseDto.getId());
        Assertions.assertEquals(updateDto.getUsername(), responseDto.getUsername());
    }
}
