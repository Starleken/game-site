package org.leafall.mainservice.mapper;

import org.leafall.mainservice.dto.comment.CommentHistoryDto;
import org.leafall.mainservice.dto.game.GameHistoryDto;
import org.leafall.mainservice.dto.genre.GenreHistoryDto;
import org.leafall.mainservice.dto.post.PostHistoryDto;
import org.leafall.mainservice.dto.review.ReviewHistoryDto;
import org.leafall.mainservice.entity.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    CommentHistoryDto mapToHistoryDto(CommentEntity entity);
    GameHistoryDto mapToHistoryDto(GameEntity entity);
    GenreHistoryDto mapToHistoryDto(GenreEntity entity);
    PostHistoryDto mapToHistoryDto(PostEntity entity);
    ReviewHistoryDto mapToHistoryDto(ReviewEntity entity);
}
