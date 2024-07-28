package org.leafall.mainservice.service;

import org.leafall.mainservice.dto.post.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    Page<PostResponseShortDto> findAll(int page, int size);
    PostResponseDto findById(long id);
    PostResponseDto create(PostCreateDto createDto);
    PostResponseDto update(PostUpdateDto updateDto);
    void deleteById(long id);
    PostResponseDto changeImage(PostChangeImageDto imageDto);
}
