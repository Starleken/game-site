package com.leafall.accountsservice.service;

import com.leafall.accountsservice.dto.post.*;

import java.util.List;

public interface PostService {

    List<PostResponseShortDto> findAll();
    PostResponseDto findById(long id);
    PostResponseDto create(PostCreateDto createDto);
    PostResponseDto update(PostUpdateDto updateDto);
    void deleteById(long id);
    PostResponseDto changeImage(PostChangeImageDto imageDto);
}
