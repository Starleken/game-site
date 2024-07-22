package org.leafall.authservice.core.utils.equals;

import org.junit.jupiter.api.Assertions;
import org.leafall.authservice.dto.user.UserCreateDto;
import org.leafall.authservice.dto.user.UserResponseDto;
import org.leafall.authservice.dto.user.UserSignupDto;
import org.leafall.authservice.dto.user.UserUpdateDto;
import org.leafall.authservice.entity.UserEntity;

import static org.junit.jupiter.api.Assertions.*;

public abstract class UserEqualsUtils {

    public static void equal(UserEntity entity, UserResponseDto dto) {
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getUsername(), dto.getUsername());
        assertEquals(entity.getRole(), dto.getRole());
    }

    public static void equal(UserCreateDto createDto, UserResponseDto responseDto) {
        assertEquals(createDto.getEmail(), responseDto.getEmail());
        assertEquals(createDto.getUsername(), responseDto.getUsername());
        assertEquals(createDto.getRole(), responseDto.getRole());
    }

    public static void equal(UserUpdateDto updateDto, UserResponseDto responseDto) {
        assertEquals(updateDto.getId(), responseDto.getId());
        assertEquals(updateDto.getUsername(), responseDto.getUsername());
    }

    public static void equal(UserEntity entity, UserSignupDto signupDto) {
        assertEquals(signupDto.getEmail(), entity.getEmail());
        assertEquals(signupDto.getUsername(), entity.getUsername());
        assertNotEquals(signupDto.getPassword(), entity.getPassword());
    }
}
