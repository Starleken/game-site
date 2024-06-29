package com.leafall.accountsservice.core.utils.dto;

import com.leafall.accountsservice.core.utils.FakerUtils;
import com.leafall.accountsservice.dto.comment.CommentCreateDto;
import com.leafall.accountsservice.dto.comment.CommentUpdateDto;
import com.leafall.accountsservice.entity.CommentEntity;

import static com.leafall.accountsservice.core.utils.FakerUtils.*;

public abstract class CommentDtoUtils {

    public static CommentCreateDto generateCreateDto(long postId) {
        return CommentCreateDto.builder()
                .content(faker.lorem().paragraph())
                .toPostId(postId)
                .build();
    }

    public static CommentUpdateDto generateUpdateDto(long commentId) {
        return CommentUpdateDto.builder()
                .id(commentId)
                .content(faker.lorem().paragraph())
                .build();
    }
}
