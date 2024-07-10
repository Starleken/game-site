package com.example.fileservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class FileUploadDtoWrapper {

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<MultipartFile> files;
}
