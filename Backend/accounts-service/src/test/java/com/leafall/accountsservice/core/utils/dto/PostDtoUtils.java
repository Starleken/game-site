package com.leafall.accountsservice.core.utils.dto;

import com.leafall.accountsservice.core.utils.FileUtils;
import com.leafall.accountsservice.dto.post.PostChangeImageDto;
import com.leafall.accountsservice.dto.post.PostCreateDto;
import com.leafall.accountsservice.dto.post.PostUpdateDto;

import static com.leafall.accountsservice.core.utils.FakerUtils.*;
import static com.leafall.accountsservice.core.utils.FileUtils.*;

public abstract class PostDtoUtils {

    public static PostCreateDto generateCreateDto() {
        PostCreateDto dto = new PostCreateDto();
        dto.setContent(faker.lorem().characters());
        dto.setHeader(faker.name().title());
        return dto;
    }

    public static PostUpdateDto generateUpdateDto(Long id) {
        return PostUpdateDto.builder()
                .id(id)
                .content(faker.lorem().characters())
                .header(faker.name().title())
                .build();
    }

    public static PostChangeImageDto generateChangeImageDto(Long id) {
        return PostChangeImageDto.builder()
                .id(id)
                .build();
    }
}
