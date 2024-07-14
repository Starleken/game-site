package com.leafall.accountsservice.mapper;

import com.leafall.accountsservice.dto.comment.CommentHistoryDto;
import com.leafall.accountsservice.dto.game.GameHistoryDto;
import com.leafall.accountsservice.dto.genre.GenreHistoryDto;
import com.leafall.accountsservice.dto.post.PostHistoryDto;
import com.leafall.accountsservice.dto.review.ReviewHistoryDto;
import com.leafall.accountsservice.dto.user.UserHistoryDto;
import com.leafall.accountsservice.entity.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    CommentHistoryDto mapToHistoryDto(CommentEntity entity);
    GameHistoryDto mapToHistoryDto(GameEntity entity);
    GenreHistoryDto mapToHistoryDto(GenreEntity entity);
    PostHistoryDto mapToHistoryDto(PostEntity entity);
    ReviewHistoryDto mapToHistoryDto(ReviewEntity entity);
    UserHistoryDto mapToHistoryDto(UserEntity entity);
}
