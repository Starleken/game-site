package com.leafall.accountsservice.mapper;

import com.leafall.accountsservice.dto.file.FileResponseDto;
import com.leafall.accountsservice.entity.FileEntity;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface FileMapper {

    FileResponseDto mapToDto(UUID id, String fileUrl, String originalFileName);

    FileEntity map(String fileUrl, String originalFileName);

}
