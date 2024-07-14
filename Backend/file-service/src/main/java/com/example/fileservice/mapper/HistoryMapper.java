package com.example.fileservice.mapper;

import com.example.fileservice.dto.FileHistoryDto;
import com.example.fileservice.entity.FileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    FileHistoryDto mapToDto(FileEntity entity);
}
