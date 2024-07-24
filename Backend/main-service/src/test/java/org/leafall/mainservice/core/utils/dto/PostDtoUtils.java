package org.leafall.mainservice.core.utils.dto;

import org.leafall.mainservice.dto.post.PostChangeImageDto;
import org.leafall.mainservice.dto.post.PostCreateDto;
import org.leafall.mainservice.dto.post.PostUpdateDto;

import static org.leafall.mainservice.core.utils.FakerUtils.*;

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
