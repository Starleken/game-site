package com.leafall.accountsservice.dto.post;

import com.leafall.accountsservice.dto.comment.CommentResponseDto;
import dto.FileResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Data
@Builder
public class PostResponseDto {

    private Long id;
    private String header;
    private String content;
    private FileResponseDto image;
    private String createdBy;
    private Long createdAt;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CommentResponseDto> comments;
}
