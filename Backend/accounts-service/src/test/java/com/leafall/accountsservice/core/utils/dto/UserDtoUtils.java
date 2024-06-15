package com.leafall.accountsservice.core.utils.dto;

import com.leafall.accountsservice.core.utils.FakerUtils;
import com.leafall.accountsservice.dto.user.ChangeEmailDto;
import com.leafall.accountsservice.dto.user.ChangePasswordDto;
import com.leafall.accountsservice.dto.user.UserCreateDto;
import com.leafall.accountsservice.dto.user.UserUpdateDto;
import com.leafall.accountsservice.entity.UserEntity;
import com.leafall.accountsservice.entity.UserEntityRole;

import static com.leafall.accountsservice.core.utils.FakerUtils.*;

public abstract class UserDtoUtils {

    public static UserCreateDto generateCreateDto() {
        return UserCreateDto.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .role(UserEntityRole.USER)
                .username(faker.name().username())
                .build();
    }

    public static UserCreateDto generateCreateDto(String username, String email) {
        return UserCreateDto.builder()
                .email(email)
                .password(faker.internet().password())
                .role(UserEntityRole.USER)
                .username(username)
                .build();
    }

    public static UserUpdateDto generateUpdateDto(UserEntity savedEntity) {
        return UserUpdateDto.builder()
                .id(savedEntity.getId())
                .username(faker.name().username())
                .build();
    }

    public static UserUpdateDto generateUpdateDto(UserEntity savedEntity, String username) {
        return UserUpdateDto.builder()
                .id(savedEntity.getId())
                .username(username)
                .build();
    }

    public static UserUpdateDto generateUpdateDto(long id) {
        return UserUpdateDto.builder()
                .id(id)
                .username(faker.name().username())
                .build();
    }

    public static ChangePasswordDto generatePasswordDto(UserEntity user, String oldPassword) {
        return ChangePasswordDto.builder()
                .id(user.getId())
                .oldPassword(oldPassword)
                .newPassword(faker.internet().password())
                .build();
    }

    public static ChangePasswordDto generatePasswordDto(Long id, String oldPassword) {
        return ChangePasswordDto.builder()
                .id(id)
                .oldPassword(oldPassword)
                .newPassword(faker.internet().password())
                .build();
    }

    public static ChangeEmailDto generateEmailDto(UserEntity user, String newEmail) {
        return ChangeEmailDto.builder()
                .id(user.getId())
                .newEmail(newEmail)
                .build();
    }

    public static ChangeEmailDto generateEmailDto(Long id, String newEmail) {
        return ChangeEmailDto.builder()
                .id(id)
                .newEmail(newEmail)
                .build();
    }
}
