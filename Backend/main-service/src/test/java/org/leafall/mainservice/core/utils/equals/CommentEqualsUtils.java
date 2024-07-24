package org.leafall.mainservice.core.utils.equals;

import org.leafall.mainservice.dto.comment.CommentCreateDto;
import org.leafall.mainservice.dto.comment.CommentResponseDto;
import org.leafall.mainservice.dto.comment.CommentUpdateDto;

import static org.junit.jupiter.api.Assertions.*;

public abstract class CommentEqualsUtils {

    public static void equal(CommentResponseDto entity, CommentCreateDto createDto) {
        assertEquals(createDto.getContent(), entity.getContent());
    }

    public static void equal(CommentResponseDto entity, CommentUpdateDto updateDto) {
        assertEquals(updateDto.getId(), entity.getId());
        assertEquals(updateDto.getContent(), entity.getContent());
    }
}
