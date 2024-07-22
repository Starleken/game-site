package org.leafall.authservice.core.utils.dto;

import org.leafall.authservice.dto.user.*;
import org.leafall.authservice.entity.UserEntity;
import org.leafall.authservice.entity.UserEntityRole;

import static org.leafall.authservice.core.utils.FakerUtils.faker;

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

    public static UserSignupDto generateSignupDto() {
        return UserSignupDto.builder()
                .email(faker.internet().emailAddress())
                .username(faker.name().username())
                .password(faker.internet().password())
                .build();
    }

    public static LoginRequestDto generateLoginRequestDto() {
        return LoginRequestDto.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build();
    }

    public static LoginRequestDto generateLoginRequestDto(String email, String password) {
        return LoginRequestDto.builder()
                .email(email)
                .password(password)
                .build();
    }
}
