package org.leafall.authservice.mapper;

import org.leafall.authservice.dto.user.UserHistoryDto;
import org.leafall.authservice.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    UserHistoryDto mapToHistoryDto(UserEntity entity);
}
