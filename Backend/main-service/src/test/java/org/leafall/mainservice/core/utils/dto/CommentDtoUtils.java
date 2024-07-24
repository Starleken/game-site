package org.leafall.mainservice.core.utils.dto;

import org.leafall.mainservice.dto.comment.CommentCreateDto;
import org.leafall.mainservice.dto.comment.CommentUpdateDto;

import static org.leafall.mainservice.core.utils.FakerUtils.*;

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
