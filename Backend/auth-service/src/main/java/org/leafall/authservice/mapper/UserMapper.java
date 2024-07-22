package org.leafall.authservice.mapper;

import org.leafall.authservice.dto.user.*;
import org.leafall.authservice.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto mapToDto(UserEntity entity);

    UserResponseShortDto mapToShortDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    UserEntity mapToEntity(UserCreateDto createDto);

    UserEntity mapToEntity(UserSignupDto signupDto);

    default void update(UserEntity entity, UserUpdateDto updateDto) {
        entity.setUsername(updateDto.getUsername());
    }
}
