package com.example.fileservice.mapper;


import com.example.fileservice.dto.FileResponseDto;
import com.example.fileservice.entity.FileEntity;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface FileMapper {

    FileResponseDto mapToDto(UUID id, String fileUrl, String originalFileName);

    FileEntity map(String fileUrl, String originalFileName);

}
