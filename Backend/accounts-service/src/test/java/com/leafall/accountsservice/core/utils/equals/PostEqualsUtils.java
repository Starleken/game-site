package com.leafall.accountsservice.core.utils.equals;

import com.leafall.accountsservice.dto.post.PostCreateDto;
import com.leafall.accountsservice.dto.post.PostResponseDto;
import com.leafall.accountsservice.dto.post.PostUpdateDto;
import com.leafall.accountsservice.entity.PostEntity;
import jakarta.persistence.PostUpdate;
import org.junit.jupiter.api.Assertions;

public abstract class PostEqualsUtils {

    public static void equal(PostEntity entity, PostResponseDto responseDto) {
        Assertions.assertEquals(entity.getId(), responseDto.getId());
        Assertions.assertEquals(entity.getHeader(), responseDto.getHeader());
        Assertions.assertEquals(entity.getContent(), responseDto.getContent());
        Assertions.assertEquals(entity.getCreatedBy(), responseDto.getCreatedBy());
    }

    public static void equal(PostCreateDto createDto, PostResponseDto responseDto) {
        Assertions.assertEquals(createDto.getContent(), responseDto.getContent());
        Assertions.assertEquals(createDto.getHeader(), responseDto.getHeader());
    }

    public static void equal(PostUpdateDto updateDto, PostResponseDto responseDto) {
        Assertions.assertEquals(updateDto.getContent(), responseDto.getContent());
        Assertions.assertEquals(updateDto.getHeader(), responseDto.getHeader());
    }
}
