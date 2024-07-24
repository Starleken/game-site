package org.leafall.mainservice.dto.post;

import org.leafall.fileservicestarter.dto.FileResponseDto;
import org.leafall.mainservice.dto.comment.CommentResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
