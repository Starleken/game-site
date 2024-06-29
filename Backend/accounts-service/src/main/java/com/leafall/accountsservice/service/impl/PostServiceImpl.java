package com.leafall.accountsservice.service.impl;

import com.leafall.accountsservice.dto.post.*;
import com.leafall.accountsservice.entity.PostEntity;
import com.leafall.accountsservice.mapper.PostMapper;
import com.leafall.accountsservice.repository.PostRepository;
import com.leafall.accountsservice.service.FileService;
import com.leafall.accountsservice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.leafall.accountsservice.utils.ExceptionUtils.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final FileService fileService;
    private final PostMapper postMapper;

    @Override
    public List<PostResponseShortDto> findAll() {
        var entities = postRepository.findAll();

        return entities.stream()
                .map(postMapper::mapToShortDto)
                .toList();
    }

    @Override
    public PostResponseDto findById(long id) {
        var found = postRepository.findById(id)
                .orElseThrow(() -> getEntityNotFoundException(PostEntity.class));

        var responseDto = postMapper.mapToDto(found);
        responseDto.setImage(fileService.getById(found.getImageId()));
        return responseDto;
    }

    @Override
    public PostResponseDto create(PostCreateDto createDto) {
        var entity = postMapper.mapToEntity(createDto);
        var image = fileService.upload(createDto.getFile());
        entity.setImageId(image.getId());
        entity.setCreatedAt(new Date());

        var saved = postRepository.save(entity);
        var responseDto = postMapper.mapToDto(saved);
        responseDto.setImage(image);
        return responseDto;
    }

    @Override
    public PostResponseDto update(PostUpdateDto updateDto) {
        var found = postRepository.findById(updateDto.getId())
                .orElseThrow(() -> getEntityNotFoundException(PostEntity.class));
        postMapper.update(found, updateDto);

        var saved = postRepository.save(found);
        var responseDto = postMapper.mapToDto(saved);
        responseDto.setImage(fileService.getById(saved.getImageId()));
        return responseDto;
    }

    @Override
    public void deleteById(long id) {
        postRepository.findById(id)
                .orElseThrow(() -> getEntityNotFoundException(PostEntity.class));

        postRepository.deleteById(id);
    }

    @Override
    public PostResponseDto changeImage(PostChangeImageDto imageDto) {
        var found = postRepository.findById(imageDto.getId())
                .orElseThrow(() -> getEntityNotFoundException(PostEntity.class));

        var image = fileService.upload(imageDto.getFile());
        found.setImageId(image.getId());

        var saved = postRepository.save(found);
        var responseDto = postMapper.mapToDto(saved);
        responseDto.setImage(fileService.getById(image.getId()));
        return responseDto;
    }
}
