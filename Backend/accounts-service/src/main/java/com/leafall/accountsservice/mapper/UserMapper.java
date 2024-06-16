package com.leafall.accountsservice.mapper;

import com.leafall.accountsservice.dto.user.UserCreateDto;
import com.leafall.accountsservice.dto.user.UserResponseDto;
import com.leafall.accountsservice.dto.user.UserResponseShortDto;
import com.leafall.accountsservice.dto.user.UserUpdateDto;
import com.leafall.accountsservice.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto mapToDto(UserEntity entity);

    UserResponseShortDto mapToShortDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    UserEntity mapToEntity(UserCreateDto createDto);

    default void update(UserEntity entity, UserUpdateDto updateDto) {
        entity.setUsername(updateDto.getUsername());
    }
}
